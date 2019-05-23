# xs2a-gateway
[![Build Status](https://travis-ci.com/adorsys/xs2a-gateway.svg?branch=develop)](https://travis-ci.com/adorsys/xs2a-gateway)

There are various ways for a bank to implement a PSD2 compliant XS2A interface. Don’t waste time in connecting different banks with different approaches into your application. Use the free of charge XS2A adapter and concentrate on your true value proposition! 
## Who we are
[adorsys](https://adorsys.de/en/index.html) is a company who works ever since the very beginning of PSD2 with its requirements and implicit tasks.
We help banks to be PSD2 complaint (technical and legal terms). To speed up the process we provide this open source XS2A interface, specified by Berlin Group,
that can be connected to your middleware system.
You can check your readiness for PSD2 Compliance and other information via [our Web-site](https://adorsys.de/en/psd2).

## What this Project is about

### Key Challenge for a TPP in Europe

PSD2 as the first regulatory driven Open Banking initiative offers many opportunities for both Banks and Third Party Providers. TPPs can use the account information and payment services provided by the banks in order to offer new innovative services to the end users. The more banks a TPP can interact with the more users it can reach with its application, which in consequence raises the value of the application itself.  
However, being able to interact with many banks can be a time and cost consuming challenge when developing and maintaining an application. Even though PSD2 sets a standard for bank interfaces, much space for implementation options remains. A bank, therefore, can have an own PSD2 compliant solution or have implemented one of the mayor PSD2 standards, like Open Banking UK, Berlin Group or STET. A PSD2 adapter must be able to process the different messages correctly and react fast to changes on the XS2A interfaces. 

### XS2A Adapter Solution by adorsys

The XS2A Adapter is a service component for Multi-Banking Applications. On one hand, you can interact with the adapter through an own interface based on the Berlin Group Specification. On the other Hand, it can communicate with different PSD2-interfaces from various Banks in Germany and Europe. Our solution is Open Source and free of charge. It can easily be embedded in your application using either a java or a REST client. With the help of a growing community our adapter is kept up-to-date regarding the changes on the XS2A interfaces of the banks. Also, as part of our product vision, a core team will be interacting with the community in order to keep connecting new banks to the adapter. 

### High level architecture
![High level architecture](docs/img/high%20level%20architecture.png)

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
* adapters/deutsche-bank-adapter
* ... all other bank adapters adapters/*-adapter

### Standalone mode

If you want to install our *xs2a-gateway* as standalone application, you need just run next commands 
* cd xs2a-gateway-app/target/
* java -jar xs2a-gateway-app.jar

Application will be available by address http://localhost:8999/swagger-ui.html

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
