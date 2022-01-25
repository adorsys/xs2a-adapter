# XS2A Adapter
[![Build Status](https://github.com/adorsys/xs2a-adapter/workflows/Develop%20CI/badge.svg)](https://github.com/adorsys/xs2a-adapter/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=adorsys_xs2a-adapter&metric=alert_status)](https://sonarcloud.io/dashboard?id=adorsys_xs2a-adapter)

There are various ways for a bank to implement a PSD2 compliant XS2A interface. Don’t waste time in connecting different banks with different approaches into your application. Use the free of charge XS2A adapter and concentrate on your true value proposition!

## Licensing model change to dual license: AGPL v.3 or commercial license

**Attention: this open-source project will change its licensing model as of 01.01.2022!**

Constantly evolving and extending scope, production traffic and support in open banking
world call for high maintenance and service investments on our part.

Henceforth, adorsys will offer all versions higher than v.0.1.16 of Adapter under a
dual-license model. Thus, this repository will be available either under Affero GNU General
Public License v.3 (AGPL v.3) or alternatively under a commercial license agreement.

We would like to thank all our users for their trust so far and are convinced that we will be
able to provide an even better service going forward.

For more information, advice for your implementation project or if your use case requires
more time to adapt this change, please contact us at psd2@adorsys.com .

For additional details please see the section [FAQ on Licensing Change](https://github.com/adorsys/xs2a-adapter#faq-on-licensing-change).

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

XS2A Adapter publishes a new release every 3 to 6 months. All released features, fixes, details, etc. can be found
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

This project is dual licensed under Affero GNU General Public License v.3 (AGPL v.3) or alternatively under a commercial license agreement - see the [LICENSE](LICENSE) file for details.

For commercial inquiries please contact us at psd2@adorsys.com.

For additional details please see the section: FAQ on Licensing Change.

## FAQ on Licensing Change

**What is a dual-licensing model?**

Under a dual-licensing model, our product is available under two licenses:

1. [The Affero GNU General Public License v3 (AGPL v3)](https://www.gnu.org/licenses/agpl-3.0.en.html)
2. A proprietary commercial license

If you are a developer or business that would like to review our products in detail, test and implement in your open-source projects and share the changes back to the community, the product repository is freely available under AGPL v3.

If you are a business that would like to implement our products in a commercial setting and would like to protect your individual changes, we offer the option to license our products under a commercial license.

This change will still allow free access and ensure openness under AGPL v3 but with assurance of committing any alterations or extensions back to the project and preventing redistribution of such implementations under commercial license.

**Will there be any differences between the open-source and commercially licensed versions of your products?**

Our public release frequency will be reduced as our focus shifts towards the continuous maintenance of the commercial version. Nevertheless, we are committed to also provide open-source releases of our products on a regular basis as per our release policy.

For customers with a commercial license, we will offer new intermediate releases in a more frequent pace.

**Does this mean that this product is no longer open source?**

No, the product will still be published and available on GitHub under an OSI-approved open-source license (AGPL v3).

**What about adorsys’ commitment to open source? Will adorsys provide future product releases on GitHub?**

We at adorsys are committed to continue actively participating in the open-source community. Our products remain licensed under OSI-approved open-source licenses, and we are looking forward to expanding our product portfolio on GitHub even further.

**How does the change impact me if I already use the open-source edition of your product?**

All currently published versions until v1.0 will remain under their current Apache 2.0 license and its respective requirements and you may continue using it as-is. To upgrade to future versions, you will be required to either abide by the requirements of AGPL v3, including documenting and sharing your implemented changes to the product when distributing, or alternatively approach us to obtain a commercial license.

**What if I cannot adjust to the new licensing model until 01.01.2022? Can I extend the deadline?**

We understand that adjustment to licensing changes can take time and therefore are open to discuss extension options on an individual basis. For inquiries please contact us at psd2@adorsys.com.

**Which versions of the product are affected?**

All versions of Open Banking Gateway after v1.0 will be affected by the licensing changes and move to a dual-licensing model.

**What will happen to older, Apache 2.0 licensed product versions?**

All older Apache 2.0 licensed versions prior and including v1.0 will remain available under their existing license.

**What open-source products from Adorsys are affected by the licensing change?**

The following products are affected:

- [XS2A Core](https://github.com/adorsys/xs2a)
- [XS2A Sandbox & ModelBank](https://github.com/adorsys/XS2A-Sandbox)
- [Open Banking Gateway](https://github.com/adorsys/open-banking-gateway) incl. [XS2A Adapters](https://github.com/adorsys/xs2a-adapter)
- [SmartAnalytics](https://github.com/adorsys/smartanalytics)
- [Datasafe](https://github.com/adorsys/datasafe)

**I’m using one of these products indirectly via some software integrator. How does the licensing change affect me?**

The licensing change does not affect you as user, but it is relevant to your provider who has used our product in their solution implementation. In case of uncertainty please contact your service provider or approach us at psd2@adorsys.com.
