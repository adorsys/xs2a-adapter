# XS2A Adapter
[![Build Status](https://github.com/adorsys/xs2a-adapter/workflows/Develop%20CI/badge.svg)](https://github.com/adorsys/xs2a-adapter/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=adorsys_xs2a-adapter&metric=alert_status)](https://sonarcloud.io/dashboard?id=adorsys_xs2a-adapter)

This is the try-out version of adorsys XS2A Adapters: an open source (AGPL v3) solution that provide XS2A connectivity to the largest retail banks in Germany.

If you are looking for a strong base framework to build up XS2A connectivity capabilities to other banks or as an initial foundation for your own gateway (see also adorsys [Open Banking Gateway](https://github.com/adorsys/open-banking-gateway) project), we would be thrilled to cooperate with you and share our know-how of the framework and overall open finance and development expertise.

If you are an organisation that would like to commercially use our solutions beyond AGPL v3 requirements, we are open to discuss alternative individual licensing options. If you are interested in working with us or have any other inquiries, please contact us under [psd2@adorsys.com](mailto:psd2@adorsys.com).

There are various ways for a bank to implement a PSD2 compliant XS2A interface. Don’t waste time in connecting different banks with different approaches into your application. Use the free of charge XS2A adapter and concentrate on your true value proposition!

## Who we are

[Adorsys](https://adorsys.com/en/) is a company who works ever since the very beginning of PSD2 with its requirements and implicit tasks.
We help banks to be PSD2 complaint (technical and legal terms). To speed up the process we provide this open source XS2A interface,
that can be connected to your middleware system.
You can check your readiness for PSD2 Compliance and other information via [adorsys — Innovative & market-driven business models](https://adorsys.com/en/).

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
If you need details about managing ASPSP Registry please refer to this [document](docs/aspsp_registry.md).

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
IDP Server, also user credentials are passed between a TPP, and an ASPSP as it would be a usual Embedded approach.

More details are on the [Crealogix API Store](https://preview.wso2-clx.crealogix-online.com/store/apis/info?name=PSD2Pre-StepAuthorizationAPI&version=1.0.6&provider=admin).

Crealogix solution is used by DKB.

## WireMock Mode

XS2A Adapter has a feature for testing a bank connection without actually communicating with a bank. We have written stubs
of real bank responses so you can give a try for your solution. This feature is called a `WireMock Mode`.

To activate it, a user will need to set `sa2a-adapter:wire-mock:mode` to `true` within the `application.yml` file. It will
start a WiremockHttpClient, with a build in WireMock server, instead of a default ApacheHttpClient.

Not all adapters have written stubs though. Responses available for the next adapters:
- adorsys-adapter
- deutsche-bank-adapter
- fiducia-adapter
- ing-adapter
- sparkasse-adapter
- verlag-adapter
- santander-adapter
- unicredit-adapter
- commerzbank-adapter
- comdirect-adapter

New stubs will be added in time.

The XS2A Adapter also provides an easy way to connect to a standalone WireMock server in case a user will want to have
one running separately. For connecting with a standalone WireMock you will want to have a `WireMock Mode` on and provide
a URL to the server as a value of `sa2a-adapter:wire-mock:standalone:url` property.

More details on the `WireMock Mode` can be found [here](https://adorsys.github.io/xs2a-adapter/wiremock-mode).

## Examples of XS2A flows

In case you are not very comfortable with how all communication between a TPP and a bank is performed,
please take a look at [these examples](https://adorsys.github.io/xs2a-adapter/xs2a-flows) in a form of sequence diagrams.

For full description, please refer to the official Berlin Group PSD2 specification - <https://www.berlin-group.org/nextgenpsd2-downloads>

## Technical Details

We have provided technical description in the [arc42 document](https://adorsys.github.io/xs2a-adapter/).

## Banks peculiarities

There are some specific cases in communication with banks that an XS2A Adapter user must be aware of.

For example, `Sparkasse` and `Fiducia` always return a list of transactions in XML format, thus triggering

```bash
Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                         RequestHeaders requestHeaders,
                                                         RequestParams requestParams);
```

or calling `getTransactionList` endpoint with specified `Accept: application/json` header on these banks
will throw `NotAcceptableException`, due to format mismatching.

## Releases and versions

 All released features, fixes, details, etc. can be found
  within the Release Notes under the [Releases](https://github.com/adorsys/xs2a-adapter/releases) section
  on GitHub.

* [Release Notes](https://github.com/adorsys/xs2a-adapter/tags)

* [Roadmap for next features development](docs/roadmap.adoc)

## Authors & Contact

* **[Francis Pouatcha](mailto:fpo@adorsys.de)** - *Initial work* - [adorsys](https://www.adorsys.de)

See also the list of [contributors](https://github.com/adorsys/xs2a-adapter/graphs/contributors) who participated in this project.

For commercial support please contact **[adorsys Team](https://adorsys.de/en/psd2)**.

If you have any technical questions you can ask them in our [gitter](https://gitter.im/adorsys/xs2a-adapter) or via the [e-mail](mailto:xs2adapter@adorsys.de)

## License

This project is licensed under Affero GNU General Public License v.3 (AGPL v.3). See the [LICENSE](LICENSE) file for details. For alternative individual licensing options please contact us at [psd2@adorsys.com](mailto:psd2@adorsys.com).
