[![Build Status](https://travis-ci.org/akashvbabu/akash-demo-project.svg?branch=master)](https://travis-ci.org/akashvbabu/akash-demo-project)

### Phone Number Verification/AlphaNumeric Combination Generation

This application verifies if a provided phone number is of the format
111-111-111 or 111-1111 or 111 1111 or 111 111 1111 and generates a set 
of alphanumeric combinations using the digits from the number. 

### Running the application

To run the backend application (using java11 and mvn):

```
cd akash-demo
mvn clean package
java -jar /target/akash-demo-0.0.1-SNAPSHOT.jar
```

To run the backen application (using docker):

```
cd akash-demo
docker build -t akash-demo-backend .
docker run -d -p 8080:8080 akash-demo-backend:latest
```