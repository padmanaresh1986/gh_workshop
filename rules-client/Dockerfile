# Use Python 3.11 base image
FROM python:3.11-slim

# Set working directory
WORKDIR /app

# Copy requirements and install dependencies
COPY app/requirements.txt requirements.txt
RUN pip install -r requirements.txt

# Copy the application code
COPY app/business_rule_client.py .

# Run the Python script
CMD ["python", "business_rule_client.py"]
