## Service Layer for Digital Twin for Smart Living - Prototype
### Requirements
Java, Gradle, Docker, MongoDB

### Project Structure: 

Application is build with the Ports and Adapters application design approach in combination with CQRS pattern
CQRS: https://martinfowler.com/bliki/CQRS.html
Ports and Adapters: https://medium.com/idealo-tech-blog/hexagonal-ports-adapters-architecture-e3617bcf00a0

web: containing the exposed REST APIs for incoming traffic 
domain: including business logic and domain objects
config: including bean configuration
backend: including outgoing traffic for DB access or 3rd party systems
resources: containing application configuration

### Configure App
adapt the following properties in src/main/resources/application.properties: 

-- Database Connection
mongodb.connection=<connection string>
mongodb.database=<database>

-- Device Layer Connection
web.devicelayer.uri=<uri>
web.devicelayer.performaction=<path>

### Configure Database
To set up a mongoDB you can either go with the community version (self-hosted) or you can use the free tier from MongoDB Atlas (DBaaS) running in the cloud.

MongoDB Community
https://hub.docker.com/_/mongo

MongoDB Atlas
https://www.mongodb.com/docs/atlas/getting-started/ 

Add Test data with:
- servicelayer.slService.json

### Open API UI
port = src/main/resources/application.properties --> server.port
URL for swagger UI = http://localhost:<port>/swagger-ui.html

#### Configure Open API for Router Functions - Documentation Reference
https://neuw.medium.com/spring-webflux-swagger-ui-and-open-api-3-api-specifications-fdbccd584c94
https://springdoc.org/#getting-started
Router Functions --> https://springdoc.org/#spring-webfluxwebmvc-fn-with-functional-endpoints

## Build with gradle
```
gradle build
java -jar build/libs/*.jar
```
-- Or integrate Spring boot configuration into IDE
-- Application listen on port 8080

## Build Docker Image and run on Docker

```
gradle build
docker build --tag=generic:latest -f docker-file .
docker run -p8080:8080 generic:latest
```

## Functionality

### Concept 
The Service Layer for Smart Living Digital Twin - Prototype is part of a distributed system architect containing: Service-Layer, Device-Layer & Integration-Layer
The Service Layer application is responsible for managing Smart Living.

The idea behind a SL Digital Twin is to represent different classification of DT which exist in the smart living context. E.g. Not connected car, digital subscriptions, insurance contracts, trash cans, smart home devices, simple sensors.

SL Service is represented via the SLService.class (src.main.java.domain.data.SLService.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A SLService.class object contains a general description of the service and all Things which are registered in the Device Layer application and the corresponding actions including an optional value to be executed. 

The ID od a SLService.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:service:<UUID>-<name>
```

### Error Handling
Errors will be gathered via the GlobalWebExceptionHandler.class (src.main.java.web.GlobalWebExceptionHandler.class)
To create custom error response object, adapt the corresponding return Object which will be injected after Exception handling.
