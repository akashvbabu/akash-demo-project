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

To run the backend application (using docker):

```
cd akash-demo
docker build -t akash-demo-backend .
docker run -d -p 8080:8080 akash-demo-backend:latest
```

To run the backend using the lastest prebuilt docker image:
```
docker run -d -p 8080:8080 akashdollar/akash-demo
```

To run the UI (node.js >= 10.19.0 and angular cli 9.1.7)
```
cd akash-demo-app-angular
ng build
ng serve
```

To run the UI using docker
```
cd akash-demo-app-angular
docker build -t akash-demo-app-angular .
docker run -d -p 4200:4200 akash-demo-app-angular:latest
```

To run the UI using the latest prebuild docker image:
```
docker run -d -p 4200:4200 akashdollar/akash-demo-app-angular
```

Once you have both the backend and UI running locally, navigate to http://localhost:4200 to see the working application