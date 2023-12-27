## Integration Layer Digital Twin for Smart Living - Prototype
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

### Configure Database
To set up a mongoDB you can either go with the community version (self-hosted) or you can use the free tier from MongoDB Atlas (DBaaS) running in the cloud.

MongoDB Community
https://hub.docker.com/_/mongo

MongoDB Atlas
https://www.mongodb.com/docs/atlas/getting-started/

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
The Integration Layer for Smart Living Digital Twin - Prototype is part of a distributed system architect containing: Service-Layer, Device-Layer & Integration-Layer
The Integration Layer application is an abstraction layer for all kinds of integrations for the service layer application. According to the following classification.
It encapsulates the complexity of the different Integrations classification, vendors amd technical protocols behind the different integration like weather data, infrastructure data for smart cities etc..

Integration is represented via the Integration.class (src.main.java.domain.wot.core.Integration.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A Integration.class object contains a general description of the Integration based on the WOT Thing description, SENSE WOT Description and the custom extension based on the reference architecture.

The ID od a Integration.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:integration:<UUID>-<name>
```

### Integrate new action for things

Unknown actions according to the ActionAffordance WOT Thing description must be first registered in the SupportedInvokeActions.class (Enum).
The Integration.class instance has to have an action property with the corresponding action.

Implement the action specific gateway and integrate it into the InvokeActionCommand (src.main.java.com.smartliving.digitaltwin.servicelayer.domain.action.InvokeActionCommand) component and adapt the case handling. 
If you Integrate a new vendor product please follow the instructions to integrate a new vendor.

### Integrate new vendor
To introduce a new vendor product to the system, adapt the SupportedVendors.class (enum). 

Implement the vendor specific security mechanism and action invocation. 
Create a vendor specific package in the backend package for action invocation.

### Error Handling
Errors will be gathered via the GlobalWebExceptionHandler.class (src.main.java.web.GlobalWebExceptionHandler.class)
To create custom error response object, adapt the corresponding return Object which will be injected after Exception handling. 

### Known limitations
As the name of the Service Layer for Smart Living Digital Twin - Prototype says, it is a prototype to test out a reference architecture and should therefore be handled with care. 
Due to the fact, that the application is currently only a Prototype production features are missing and some functionalities are not even implemented. 
In the context of Smart Living data security is an important factor. To meet your needs according to your data classification (like contract ids, etc.) 
use Encryption at rest and in flight or event client side field level encryption to secure your data.

The following list the known limitations of the application with no claim to completeness.

-- Missing Security features
-- Missing Automated Deployment Method including the surrounding services
-- Vendor action mapping
 



