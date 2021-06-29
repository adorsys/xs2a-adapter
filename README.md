# XS2A Adapter
[![Build Status](https://github.com/adorsys/xs2a-adapter/workflows/Develop%20CI/badge.svg)](https://github.com/adorsys/xs2a-adapter/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=adorsys_xs2a-adapter&metric=alert_status)](https://sonarcloud.io/dashboard?id=adorsys_xs2a-adapter)

There are various ways for a bank to implement a PSD2 compliant XS2A interface. Don’t waste time in connecting different banks with different approaches into your application. Use the free of charge XS2A adapter and concentrate on your true value proposition!

## Who we are

[adorsys](https://adorsys.de/en/index.html) is a company who works ever since the very beginning of PSD2 with its requirements and implicit tasks.
We help banks to be PSD2 complaint (technical and legal terms). To speed up the process we provide this open source XS2A interface,
that can be connected to your middleware system.
You can check your readiness for PSD2 Compliance and other information via [our Web-site](https://adorsys.de/en/psd2).

## What this Project is about

### Key Challenge for a TPP in Europe

PSD2 as the first regulatory driven Open Banking initiative offers many opportunities for both Banks and Third Party Providers. TPPs can use the account information and payment services provided by the banks in order to offer new innovative services to the end users. The more banks a TPP can interact with the more users it can reach with its application, which in consequence raises the value of the application itself.
However, being able to interact with many banks can be a time and cost consuming challenge when developing and maintaining an application. Even though PSD2 sets a standard for bank interfaces, much space for implementation options remains. A bank, therefore, can have an own PSD2 compliant solution or have implemented one of the mayor PSD2 standards, like Open Banking UK, Berlin Group or STET. A PSD2 adapter must be able to process the different messages correctly and react fast to changes on the XS2A interfaces.


### High level architecture

![High level architecture](docs/img/high%20level%20architecture.png)

## Running the XS2A Adapter

[The developer guide](docs/developer_guide.md) contains information necessary for launching the XS2A Adapter.

## How to write your own bank adapter

Read this short [guideline](docs/Adapter.md) to get more details

## Routing and ASPSP Registry

ASPSP Registry loads data from [aspsp-adapter-config](docs/aspsp_adapter_config_csv.md) file, 
that contains all information necessary for XS2A Adapter to communicate with banks (Sandboxes only).  
`xs2a-adapter` relies on presence of `X-GTW-ASPSP-ID` or `X-GTW-Bank-Code` request header for routing.
The former uniquely identifies an XS2A API provider in the `aspsp-registry`.
The later is a shorthand for performing a lookup in the registry using a bank code.
Note that the `aspsp-registry` supports lookup by attributes other than bank code including full-text search by name,
but only as a pre-request.
If you need details about managing ASPSP Registry please refer to this [document](/docs/aspsp_registry.md).

## Releases and versions

* XS2A Adapter reveals a new release at the beginning of each month. All released features, fixes, details, etc. can be found 
  within the Release Notes referred below. We are doing our best to follow the Adapter Roadmap mentioned below as well. 
  All release information can be found under the [Releases](https://github.com/adorsys/xs2a-adapter/releases) section 
  on GitHub.

* [Release Notes](docs/release_notes/Release_notes_0.1.4.adoc)

* [Roadmap for next features development](docs/roadmap.adoc)

## Testing API with Postman json collections

 For testing API of xs2a it is used Postman https://www.getpostman.com/
 Environment jsons with global parameter’s sets and Collections of jsons for imitation of processes flows are stored in /postman folder.
 To import Postman collections and environments follow next steps:
 1. Download Postman jsons with collections and environments to your local machine.
 2. Open Postman, press button “Import”.
 3. Choose “Import file” to import one json or “Import folder” to import all jsons within the folder, then press button “Choose Files” or “Choose Folders” and open necessary files/folders.
 4. To change settings of environments - go to “Manage Environments”, press the environment name and change variables.

 To start testing with Postman collections it is necessary to have all services running.

You may run postman tests from the command line

```bash
> newman run postman/xs2a\ adapter.postman_collection.json \
        -d postman/adapters.postman_data.json \
        --globals postman/postman_globals_local.json \
        --folder AIS \
        --folder sepa-credit-transfers \
        --folder pain.001-sepa-credit-transfers \
        --timeout-request 3000
```

## HttpLogSanitizer Whitelist

XS2A Adapter has a feature that masks sensitive data in logs, e.g. PSU-ID, ConsentId, Location, etc. By default, it veils 
all data, but starting from version 0.1.5 Adapter user will be able to partially configure HttpLogSanitizer behavior 
by providing a list of request/response body fields (Whitelist) that will not be hidden.     

There are two ways of setting up Whitelist depending on how a user utilizes the XS2A Adapter:
* As standalone application: a user will want to add field names separated by a comma on `xs2a-adapter.sanitizer.whitelist` 
property under `application.yml`. Examples are already put in Adapter YAML.
* As library: a user will want to provide a java.util.List of type String into default HttpLogSanitizer implementation - `Xs2aHttpLogSanitizer`.

__Note__: field names must be Berlin Group specification compliant, otherwise there will be no effect and data will still be masked.

## Non-XS2A Interfaces

The Adapter also covers non-PSD2 XS2A interfaces and contain services that handles such cases. 
These are OAuth2 and EmbeddedPreStep services. Please check out Swagger JSONs for details: 
[OAuth2 API](xs2a-adapter-rest-impl/src/main/resources/static/oauthapi.json) 
and [EmbeddedPreStep API](xs2a-adapter-rest-impl/src/main/resources/static/embeddedpreauthapi.json) respectively.

**EmbeddedPreStep** interface is a specific Crealogix solution that resembles OAuth2 protocol but may have no interaction with
IPD Server, also user credentials are passed between a TPP, and an ASPSP as it would be usual Embedded approach.

More details are on the [Crealogix API Store](https://preview.wso2-clx.crealogix-online.com/store/apis/info?name=PSD2Pre-StepAuthorizationAPI&version=1.0.6&provider=admin).

Crealogix solution is used by DKB.

## Authors & Contact

* **[Francis Pouatcha](mailto:fpo@adorsys.de)** - *Initial work* - [adorsys](https://www.adorsys.de)

See also the list of [contributors](https://github.com/adorsys/xs2a-adapter/graphs/contributors) who participated in this project.

For commercial support please contact **[adorsys Team](https://adorsys.de/en/psd2)**.

If you have any technical questions you can ask them in our [gitter](https://gitter.im/adorsys/xs2a-adapter) or via the [e-mail](mailto:xs2adapter@adorsys.de)

## License

This project is licensed under the Apache License version 2.0 - see the [LICENSE](LICENSE) file for details
