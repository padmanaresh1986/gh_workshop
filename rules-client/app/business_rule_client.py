import requests
import random
import json
import time
import logging
import uuid


# Create a logger
logger = logging.getLogger()
logger.setLevel(logging.INFO)
# Create a console handler
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.INFO)

# Define a log format
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
console_handler.setFormatter(formatter)

# Add handlers to the logger
logger.addHandler(console_handler)

# API URL
#API_URL = "https://business-rule-processor-git-mindovermachinestech-dev.apps.rm2.thpm.p1.openshiftapps.com/business-rule-processor/api/rules/execute"
API_URL = "http://rule-engine-route-mindovermachinestech-dev.apps.rm2.thpm.p1.openshiftapps.com/business-rule-processor/api/rules/execute"

# List of available business rules
RULES = ["CreditLimitRule", "FraudCheckRule", "EligibilityRule"]

def generate_random_customer_id():
    """Generate a random customer ID."""
    return "cust-" + str(uuid.uuid4().hex[:8])

def choose_random_rules():
    """Choose a random subset of rules to execute."""
    num_rules = random.randint(1, len(RULES))
    return random.sample(RULES, num_rules)

def invoke_business_rules():
    """Invoke business rules API and print the response."""
    customer_id = generate_random_customer_id()
    rule_names = choose_random_rules()

    payload = {
        "customerId": customer_id,
        "ruleNames": rule_names
    }

    logging.info(f"🔄 Sending request for customer ID: {customer_id} with rules: {rule_names}")

    try:
        response = requests.post(API_URL, json=payload)
        response.raise_for_status()

        result = response.json()
        logging.info(f"✅ API response: {json.dumps(result, indent=2)}")

    except requests.exceptions.RequestException as e:
        logging.error(f"❌ Failed to invoke business rules API: {str(e)}")

def main():
    """Main method to run API invocation periodically."""
    interval_seconds = 10  # Invoke every 10 seconds

    logging.info("🚀 Starting the Business Rule Client...")

    while True:
        invoke_business_rules()
        time.sleep(interval_seconds)

if __name__ == "__main__":
    main()
