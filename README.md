## Digital Twin for Smart Living - Prototype
### Requirements
Java, Gradle, Docker, MongoDB

### Project Structure:

The Digital Twin for Smart Living - Prototype is a distributed system architect containing: Service-Layer, Device-Layer & Integration-Layer

The Service Layer application is responsible for managing Smart Living (SL Services according to the following definition: 
"Smart Living is defined as the improvement of a person´s quality of life, enabled by intelligent functions and devices that provide services and applications to make a person´s life more efficient, controllable, economical, productive and sustainable on the basis of information."

The definition is set up by myself during studies, based on preliminary work of the following papers (APA citation style):
- Hinz, O., Lowin, M. & Mihale-Wilson, C. (2020). Smart Living: Der Mensch im zentrum: Ein Forschungsprojekt untersucht die Chancen und Risiken der KI-gesteuerten Umgebung. 
  Forschung Frankfurt: das Wissensmagazin, 37(1), 78-82. https://publikationen.ub.uni-frankfurt.de/frontdoor/index/index/year/2020/docId/55182
- Heimer, T., Pschorn, L. & Waiblinger, F. (2022). SmartLiving2Market 2022: Eine Studie im Auftrag des Bundesministeriums für Wirtschaft und Klimaschutz (BMWK).
  technologis Group Deutschland. https://www-smartliving-germany.de/wp-content/uploads/2022/07/SmartLiving2Market2022.pdf
- Han, M. J. N. & Kim, M. J. (2021). A critical review of the smart city in relation to citizen adoption towards sutainable smart living. 
  Habitat International, 108, 102312. https://doi.org/10.1016/j.habitatint.2021.102312/
- Giffinger,R. Fertner, C., Kramar, H., Kalasek, R., Milanovic, N. & Meijers, E. (2007). Smart cities - Ranking of Eurpean medium-sized cities. 
  https://www.smart-cities.eu/download/smart_cities_final_report.pdf
- Yasirandi, R., Lander, A. Sakinah, H. R. & Insan, I. M. (2020). IoT Products Adoption for Smart Living in Indonesia: Technology (ICoICT),
  Yogyakarta, Indonesia, 1-6. https://doi.org/10.1109/ICoICT49345.2020.9166200
- Demiris, G. & Hense, B.K. (2008). Technologies for an aging society: a systematic review of "smart home" applications. 
  Yearbook of medical infoirmatics, 3, 33-40. https://doi.org/10.1055/s-0038-1638580
- Oprwood, R., Adlam, T. D., Evans, N.M., Chadd, J. & Self, D. (2008). Evaluation of an assisted-living smart home for someone with dementia. 
  Journal of Assistive Technologies, 2(2), 13-21. https://doi.org/10.1108/17549450200800014


The idea behind a SL Digital Twin is to represent different classification of DT which exist in the smart living context. E.g. Not connected car, digital subscriptions, insurance contracts, trash cans, smart home devices, simple sensors.

SL Service is represented via the SLService.class (src.main.java.domain.data.SLService.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A SLService.class object contains a general description of the service and all Things which are registered in the Device Layer application and the corresponding actions including an optional value to be executed.
The ID od a SLService.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:service:<UUID>-<name>
```

The Integration Layer application is an abstraction layer for all kinds of integrations for the service layer application. According to the following classification.
It encapsulates the complexity of the different Integrations classification, vendors amd technical protocols behind the different integration like weather data, infrastructure data for smart cities etc..

Integration is represented via the Integration.class (src.main.java.domain.wot.core.Integration.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A Integration.class object contains a general description of the Integration based on the WOT Thing description, SENSE WOT Description and the custom extension based on the reference architecture.
The ID of an Integration.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:integration:<UUID>-<name>
```

The Device Layer application is an abstraction layer for all kinds of things for the service layer application. According to the following classification.
It encapsulates the complexity of the different Thing classification, vendors amd technical protocols behind the different products.
Thing is represented via the Thing.class (src.main.java.domain.wot.core.Thing.class)) and can be created, updated & executed with the corresponding REST API (see Open API UI/ src.main.java.web.ServiceRouterFunctions)
A Thing.class object contains a general description of the Things based on the WOT Thing description, SENSE WOT Description and the custom extension based on the reference architecture.

The ID od a Thing.class instance will be generated based on the following naming convention and is the main interaction point to implement against the application:
```
urn:<env>:thing:<UUID>-<name>
```

All Applications are build with the Ports and Adapters application design approach in combination with CQRS pattern
CQRS: https://martinfowler.com/bliki/CQRS.html
Ports and Adapters: https://medium.com/idealo-tech-blog/hexagonal-ports-adapters-architecture-e3617bcf00a0

web: containing the exposed REST APIs for incoming traffic
domain: including business logic and domain objects
config: including bean configuration
backend: including outgoing traffic for DB access or 3rd party systems
resources: containing application configuration

### Configure each App following the application README.md

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

### Error Handling
Errors will be gathered via the GlobalWebExceptionHandler.class (src.main.java.web.GlobalWebExceptionHandler.class)
To create custom error response object, adapt the corresponding return Object which will be injected after Exception handling.

### Known limitations
As the name of Repository says, it is a prototype to test out a reference architecture and should therefore be handled with care.
Due to the fact, that the application is currently only a Prototype production features are missing and some functionalities are not even implemented.
In the context of Smart Living data security is an important factor. To meet your needs according to your data classification (like contract ids, etc.)
use Encryption at rest and in flight or even client side field level encryption to secure your data.

The following list the known limitations of the application with no claim to completeness.

-- Missing Security features
-- Integration only of phillips hue and ikea as a vendor
-- Integration of light status change, change events and motion sensor
-- Missing Automated Deployment Method including the surrounding services
 



