# XS2A Adapter
[![Build Status](https://travis-ci.com/adorsys/xs2a-adapter.svg?branch=develop)](https://travis-ci.com/adorsys/xs2a-adapter)

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

## Configuring the XS2A Adapter

For configuring adapter with your custom settings, just copy adapter configuration 
file [adapter.config.properties](xs2a-adapter-service-api/src/main/resources/adapter.config.properties) 
and replace with your values. Then provide the path to your custom config file 
with `adapter.config.file.path` environment variable.
```
# Java property example
-Dadapter.config.file.path=/opt/xs2a-adapter/custom-config/custom.adapter.config.properties

# Environment variable example
env "adapter.config.file.path=/opt/xs2a-adapter/custom-config/custom.adapter.config.properties"  perl -le 'print $ENV{"adapter.config.file.path"}'
```

## Running the XS2A Adapter

1. Download the project and go to the project directory:

    ```sh
    > git clone https://github.com/adorsys/xs2a-adapter
    > cd xs2a-adapter
    ```

2. Build and run the project

    ```bash
    > mvn clean package
    > java \
        -Djavax.net.ssl.keyStoreType=pkcs12 \
        -Djavax.net.ssl.keyStore=<certificate-file> \
        -Djavax.net.ssl.keyStorePassword=<certificate-password> \
        -Dcom.sun.security.enableAIAcaIssuers=true \
        -Dpkcs12.keyStore=<key-store-file> \
        -jar xs2a-adapter-app/target/xs2a-adapter-app.jar
    
    ```

3. Open [xs2a-adapter swagger page](http://localhost:8999/swagger-ui.html) to get more details about REST Api.

4. Run postman tests for AIS and PIS flows:

    ```bash
    > newman run postman/xs2a\ adapter.postman_collection.json \
            -d postman/adapters.postman_data.json \
            --globals postman/postman_globals_local.json \
            --folder AIS \
            --folder sepa-credit-transfers \
            --folder pain.001-sepa-credit-transfers \
            --timeout-request 3000
    ```

## How to write your own bank adapter
Read this short [guideline](/docs/Adapter.md) to get more details

## Routing and ASPSP Registry
`xs2a-adapter` relies on presence of `X-GTW-ASPSP-ID` or `X-GTW-Bank-Code` request header for routing.
The former uniquely identifies an XS2A API provider in the `aspsp-registry`. 
The later is a shorthand for performing a lookup in the registry using a bank code.
Note that the `aspsp-registry` supports lookup by attributes other than bank code including full-text search by name,
but only as a pre-request. 

## Releases and versions

* [Versioning, Release and Support policy](doc/Version_Policy.md)
 
* [Release notes](doc/releasenotes.md) 
* [Roadmap for next features development](doc/roadmap.md)
 
### Testing API with Postman json collections
 
 For testing API of xs2a it is used Postman https://www.getpostman.com/
 Environment jsons with global parameter’s sets and Collections of jsons for imitation of processes flows are stored in /postman folder.
 To import Postman collections and environments follow next steps:
 1. Download Postman jsons with collections and environments to your local machine.
 2. Open Postman, press button “Import”.
 3. Choose “Import file” to import one json or “Import folder” to import all jsons within the folder, then press button “Choose Files” or “Choose Folders” and open necessary files/folders.
 4. To change settings of environments - go to “Manage Environments”, press the environment name and change variables.
 
 To start testing with Postman collections it is necessary to have all services running.
 
 
## Authors & Contact

* **[Francis Pouatcha](mailto:fpo@adorsys.de)** - *Initial work* - [adorsys](https://www.adorsys.de)

See also the list of [contributors](https://github.com/adorsys/xs2a-adapter/graphs/contributors) who participated in this project.

For commercial support please contact **[adorsys Team](https://adorsys.de/en/psd2)**.

## License

This project is licensed under the Apache License version 2.0 - see the [LICENSE](LICENSE) file for details
