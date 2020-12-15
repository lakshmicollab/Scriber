# Scriber
________________________________________________________________________________________________________________________

## Link to Github Backend Repo: https://github.com/dsobers25/Scriber
    - ai recognition:
        - on branch: ai-image-recog
    - demo version demonstrated:
        - branch: deron-dev-2
## Environment setup:
    - JDK 11
    - IAM User Credentials
        - access and secret key IDs
            - used for accessing AWS services
            - must be listed in Environment variables in IDE
                - can also be listed in system variables
                - ex: AWS_SECRET_KEY:XXXXXXXXX // AWS_ACCESS_KEY:XXXXXXXX
    - Framework
        -Spring Boot REST API
## Services used:
    - Amazon RDS
        - use IAM user to create an instance of RDS (keep credentials safe)
            - May be prompted to create an additional IAM user if using root account
                - This IAM user should have AdminFullAccesspermissions
        - develop instance details
        - take note of the endpoint
        - When connecting to spring boot application
            - insert DB settings through application.properties file
                -endpoint: how the application connects to the DB
                    - ex statement: spring.datasource.url=jdbc:mysql://<DBEndpoint>/<DBname>
                - include credentials of IAM user
                    - ex statement:
                        spring.datasource.username=admin
                        spring.datasource.password=admin
    - Amazon Textract: automate through sdk
    - Amazon Comprehend: Trained models using data we provided
        -Model Training
            - Custom Entity Detection:
                - Requirements:
                    - 1000 records in 1 dataset for each model
                    - each model can only train one(1) entity
                        -ie: no running 2k records for 2 entities in one dataset
    - Amazon Rekognition: trained models using images uploaded to s3 bucket
        - AmazonRekognitionFullAccessPermission necessary
        - Model Training
            - Image Recognition:
                - custom labels needed: 2 minimum
                - amount of images needed: 30 minimum
                - bounding box for custom label assignment
    - Amazon s3:
        - set-up empty s3 bucket
        - permissions required:
            - AmazonS3ReadOnlyAccess

## Sites for dataset findings/generating:
    - Kaggle.com
    - randomlists.com