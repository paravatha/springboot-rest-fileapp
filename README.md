# springboot-rest-fileapp

#Overview: 
This project show cases REST API using Spring Boot and In-Memory Database.

*This RESTFul API spring-boot application provides the following APIs:*

* API to upload a file with a few meta-data fields. Persist meta-data in persistence store (In memory DB and store the content on a file system).
* API to get file meta-data.
* API to download content stream.
* API to search for file IDs with a search criterion.
* A scheduler to poll for new files uploaded in the last hour and send an email.

#Setup 
This is a self contained project, follow the below steps to setup the project and test

1. Edit src/main/resources/application.properties accordingly. 
2. Install and use Postman to test API end-points. 

After setup, API end-points can be found at
http://localhost:8080/swagger-ui.html#!/file-controller/
