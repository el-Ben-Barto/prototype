## Service Layer for Smart Living Digital Twin - Prototype
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

-- Smart Home Vendor Configuration
--> Phillips Hue
vendor.hue.baseurl=<Address of the PHillips Hue Hub>
vendor.hue.resource=/clip/v2/resource/device
vendor.hue.event=/eventstream/clip/v2
vendor.hue.username=<generated username>
vendor.hue.clientkey=<generated clientkey>
vendor.hue.documentation=https://developers.meethue.com/develop/hue-api-v2/api-reference/

--> Ikea Dirigera
Please use the library https://github.com/wjtje/dirigera to get auth token from dirigera hub
Get Devices:
https://192.168.178.55:8443/v1/devices 
Auth: Bearer Token

Auth Code
https://192.168.178.55:8443/v1/oauth/authorize?audience=homesmart.local&response_type=code&code_challenge=hash&code_challenge_method=S256

Get Access Token or use script
https://192.168.178.55:8443/v1/oauth/token



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
The Device Layer for Smart Living Digital Twin - Prototype is part of a distributed system architect containing: Service-Layer, Device-Layer & Integration-Layer
The Device Layer application is an abstraction layer for all kinds of things for the service layer application. According to the following classification.
It encapsulates the complexity of the different Thing classification, vendors amd technical protocols behind the different products.
--> Add Picture here

Thing is represented via the Thing.class (src.main.java.domain.wot.core.Thing.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A Thing.class object contains a general description of the Things based on the WOT Thing description, SENSE WOT Description and the custom extension based on the reference architecture. 
--> Cite myself, Cite the rest 

The ID od a Thing.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:thing:<UUID>-<name>
```

### Integrate new action for things

Unknown actions according to the ActionAffordance WOT Thing description must be first registered in the SupportedInvokeActions.class (Enum).
The Thing.class instance has to have an action property with the corresponding action.

Implement the action specific gateway and integrate it into the InvokeActionCommand (src.main.java.com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionCommand) component and adapt the case handling. 
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
-- Integration only of phillips hue as vendor
-- Integration of light status change, change events and motion sensor
-- Missing Automated Deployment Method including the surrounding services
-- Missing Unit Test 
-- Vendor action mapping
 



