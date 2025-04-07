import os
from github import Github
from openai import OpenAI

# Get environment variables
REPO_NAME = os.getenv("GITHUB_REPOSITORY")
PR_NUMBER = int(os.getenv("PR_NUMBER"))
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
AIT_API_KEY = os.getenv("AIT_API_KEY")

# Initialize GitHub client
gh = Github(GITHUB_TOKEN)
repo = gh.get_repo(REPO_NAME)

def get_diff():
    try:
        print(f"PR Number {PR_NUMBER}")
        pr = repo.get_pull(PR_NUMBER)
        print(f"PR  {pr}")
        files = pr.get_files()
        print(f" Diff files {files}")
        diff = ""
        for file in files:
            filename = file.filename
            print(f"Loop File Name {filename}")
            patch = file.patch if hasattr(file, "patch") else ""
            print(f"Loop Patch {patch}")
            diff += f"\nFile: {filename}\n{patch}\n"

        print(f"Final diffs {diff}")

        return diff
    except Exception as e:
        print("❌ Error fetching PR diff:", e)
        return ""

def get_review_comments(diff):
    prompt = f"""
    You are a senior software engineer reviewing a pull request.
    Please analyze the following code diff and provide feedback on:
    - Code quality
    - Potential bugs
    - Naming conventions
    - Security risks
    - Suggestions for improvement

    Keep the response as simple as possible

    Code Diff:
    {diff}
    """
    print(f"Prompt {prompt}")
    print(f"AIT API key {AIT_API_KEY}")

    client = OpenAI(
        api_key=AIT_API_KEY,
        base_url="https://api.together.xyz/v1"
    )

    print(f"Chat client {client}" )

    print("Calling chat completions")
    response = client.chat.completions.create(
        model="meta-llama/Llama-3.3-70B-Instruct-Turbo",
        messages=[{"role": "user", "content": prompt}]
    )

    return response.choices[0].message.content

def post_comment(body):
    try:
        issue = repo.get_issue(number=PR_NUMBER)
        issue.create_comment(body)
        print("✅ Review comment posted.")
    except Exception as e:
        print("❌ Error posting comment:", e)

if __name__ == "__main__":
    print("🚀 Fetching diff...")
    diff = get_diff()

    if not diff.strip():
        print("⚠️ No diff found. Skipping AI review.")
    else:
        print("🤖 Generating review feedback...")
        feedback = get_review_comments(diff)
        print("💬 Posting comment to PR...")
        post_comment(feedback)