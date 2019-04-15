# xs2a-gateway
[![Build Status](https://travis-ci.com/adorsys/xs2a-gateway.svg?branch=develop)](https://travis-ci.com/adorsys/xs2a-gateway)

## Who we are
[adorsys](https://adorsys.de/en/index.html) is a company who works ever since the very beginning of PSD2 with its requirements and implicit tasks.
We help banks to be PSD2 complaint (technical and legal terms). To speed up the process we provide this open source XS2A interface, specified by Berlin Group,
that can be connected to your middleware system.
You can check your readiness for PSD2 Compliance and other information via [our Web-site](https://adorsys.de/en/psd2).

## Getting Started

### Prerequisites

- Java JDK version 1.8.x, Maven 3.x;

### Clone git repository and build a project:
```bash
$ git clone https://github.com/adorsys/xs2a-gateway
$ cd xs2a-gateway
$ mvn clean install
```
### Embedded mode

If you want to integrate xs2a-gateway into your java application you need just add next dependencies in your classpath:
* xs2a-gateway-service-impl
* deutsche-bank-adapter
* ... all other bank adapters *-adapter

### Standalone mode

If you want to install our *xs2a-gateway* as standalone application, you need just run next commands 
* cd xs2a-gateway-app/target/
* java -jar xs2a-gateway-app.jar

Application will be available by address http://localhost:8081/swagger-ui.html

### How to write your own adapter
Read this short [guideline](/docs/Adapter.md) to get more details

### Running

    -Djavax.net.ssl.keyStoreType=pkcs12
    -Djavax.net.ssl.keyStore=<filename.p12>
    -Djavax.net.ssl.keyStorePassword=<password>
    -Dcom.sun.security.enableAIAcaIssuers=true

### Built With

* [Java, version 1.8](http://java.oracle.com) - The main language of implementation
* [Maven, version 3.0](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://projects.spring.io/spring-boot/) - Spring boot as core Java framework

## Releases and versions

* [Versioning, Release and Support policy](doc/Version_Policy.md)
 
* [Release notes](doc/releasenotes.md) 
* [Roadmap for next features development](doc/roadmap.md)
 
### Testing API with Postman json collections
 
 For testing API of xs2a it is used Postman https://www.getpostman.com/
 Environment jsons with global parameter’s sets and Collections of jsons for imitation of processes flows are stored in /postman folder.
 To import Postman collections and environments follow next steps:
 1.     Download Postman jsons with collections and environments to your local machine.
 2.     Open Postman, press button “Import”.
 3.     Choose “Import file” to import one json or “Import folder” to import all jsons within the folder, then press button “Choose Files” or “Choose Folders” and open necessary files/folders.
 4.     To change settings of environments - go to “Manage Environments”, press the environment name and change variables.
 
 To start testing with Postman collections it is necessary to have all services running.
 
 
## Authors & Contact

* **[Francis Pouatcha](mailto:fpo@adorsys.de)** - *Initial work* - [adorsys](https://www.adorsys.de)

See also the list of [contributors](https://github.com/adorsys/xs2a-gateway/graphs/contributors) who participated in this project.

For commercial support please contact **[adorsys Team](https://adorsys.de/en/psd2)**.

## License

This project is licensed under the Apache License version 2.0 - see the [LICENSE](LICENSE) file for details
