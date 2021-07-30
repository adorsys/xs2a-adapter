## Running the XS2A Adapter - Developer Guide

### Prerequisites

For running or utilizing the Application you may need:

- Java 8+ JDK
- Maven
- Docker

### Certificates

In order to interact with the Banks' XS2A Interfaces, a user will need to have an eIDAS certificate. 
Qualified Certificates for Website Authentication (QWAC) is a mandatory one for establishing TLS-connection. In some cases,
Qualified Certificates for Electronic Seals (QSEAL) may be needed. The XS2A Adapter expects a user to provide a PKCS12 KeyStore
in the configuration file. More details are coming below.

### Common XS2A Adapter configuration

- `Keystore`. How to create an [adapter keystore](keystore.md). After the keystore has been created, you should specify the properties **pkcs12.keyStore** and **pkcs12.keyStorePassword**.
- `Adapters Config File`. A very basic configuration is already provided within [adapter.config.properties](../xs2a-adapter-service-api/src/main/resources/adapter.config.properties), 
also you can introduce your own custom configuration, and specify the path to it with `adapter.config.file.path` 
environment variable. Please check out this [documentation](configuration.md) if you need details about the Adapter Config File. 
- `aspsp-registry` config file. For configuring ASPSP registry you can modify [aspsp-adapter-config.csv](../xs2a-adapter-aspsp-registry/src/main/resources/aspsp-adapter-config.csv) file
and specify the location with `csv.aspsp.adapter.config.file.path` environment variable.

__Note__: be aware that `aspsp-registry` contains data for connecting with bank Sandboxes only,
if you need a production data please contact our [sales team](mailto:rene.pongratz@adorsys.com).

```shell script
# Java property example
-Dadapter.config.file.path=<path/to/adapter.config.properties>
-Dcsv.aspsp.adapter.config.file.path=<path/to/aspsp-adapter-config.csv>
-Dpkcs12.keyStore=</path/to/keystore.p12>
-Dpkcs12.keyStorePassword=<keyStorePassword>
```

### Bank's specific configurations

In order to communicate with some banks additional steps are compulsory.

#### DKB

The registration is needed. However, the process is merely simple - a user will want to call any DKB PSD2 endpoint 
with production certificates, e.g.

```
curl --location POST 'https://api.dkb.de/psd2/v1/consents' \
--cert <PATH_TO_YOUR_CERTIFICATE> \
--key <PATH_TO_YOUR_PRIVATE_KEY> \
--header 'Content-Type: application/json' \
--data-raw '{
    "access": {
        "allPsd2": "allAccounts"
    },
    "combinedServiceIndicator": "false",
    "recurringIndicator": "true",
    "validUntil": "2023-05-18",
    "frequencyPerDay": "4"
}'
```

It will return 401 UNAUTHORIZED for a successful registration.

Details about DKB [here](../adapters/crealogix-adapter/README.md)

#### Santander

The registration is needed for working with a production environment. A user will want to call a registration endpoint with
a production certificate and a private key, and provide a redirect URL:

```
curl --location POST https://api-cc.santander.de/scb-openapis/client/v1/tpp_registrations/mutual_tls \
--cert <PATH_TO_YOUR_CERTIFICATE> \
--key <PATH_TO_YOUR_PRIVATE_KEY> \
--header 'Accept: application/json'
--header 'Content-Type: application/json'
--data-raw '{"registeredRedirectUris": [“https://tpp-redirect.com/cb”]}'
```

You can specify multiple redirect URLs.

The access to the Santander Sandbox is only possible using the Santander Sandbox eIDAS Certificate, provided 
in the [Santander API Market](https://www.santander.de/privatkunden/specials/api-market/).

**For Sandbox only**, Adapter will look for specific Santander certificated under `santander_qwac` alias within your KeyStore.
Otherwise, `default_qwac` certificate will be used.

Details about Santander [here](adapters/santander-adapter.md)

#### ING

ING requires QWAC and QSEAL certificates. Adapter is expecting to have these certificates in your keystore separately.
You will need to provide certificate alias values for `ing.qwac.alias` and `ing.qseal.alias` keys respectively within `adapter.config.properties`.

**For Sandbox only**, ING shared its own certificates [here](https://developer.ing.com/openbanking/get-started/psd2)

Additional information about ING [here](../adapters/ing-adapter/README.md)

#### Bankverlag

**For Sandbox only**, a user will need to provide next values in the `adapter.config.properties`:

```
verlag.apikey.name=X-bvpsd2-test-apikey
verlag.apikey.value=tUfZ5KOHRTFrikZUsmSMUabKw09UIzGE
```

#### Deutsche Bank

**For Sandbox only**, the onboarding process needed.  
A user will need to call an onboarding endpoint with a certificate and a private key, providing a user's email and telephone
number. For instance:

```
curl --location 'POST' 'https://simulator-xs2a.db.com/register/tpp-directory/sandbox-onboarding' \
--cert <PATH_TO_YOUR_CERTIFICATE> \
--key <PATH_TO_YOUR_PRIVATE_KEY> \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email_sandbox": <your_email>,
  "phone_sandbox": <your_phone_number>
}'
```

Additional details could be found on the [Deutsche Bank Developer Portal](https://developer.db.com/products/psd2)

### Running XS2A Adapter as a standalone application

#### Running in your local machine

1. Download the project and go to the project directory:

    ```shell script
    git clone https://github.com/adorsys/xs2a-adapter
    cd xs2a-adapter
    ```

2. Build and run the project

    __Notice:__ Default application port is **8999**, and it could be changed in the [application.yml](../xs2a-adapter-app/src/main/resources/application.yml) file

    ```shell script
    mvn clean package
    ```
    Before executing the next command you should replace the next placeholders with values received at [configuration step](#common-xs2a-adapter-configuration)
    - **<path/to/keystore.p12>** with your keystore file location
    - **<path/to/adapter.config.properties>** with your adapter config file
    - **<path/to/aspsp-adapter-config.csv>** with your aspsp-registry configuration file
    - **\<keystore-password>** with a keystore password
    
    ```shell script
    java \
      -Dcom.sun.security.enableAIAcaIssuers=true \
      -Dpkcs12.keyStore=<path/to/keystore.p12> \
      -Dpkcs12.keyStorePassword=<keystore-password> \
      -Dadapter.config.file.path=<path/to/adapter.config.properties> \
      -Dcsv.aspsp.adapter.config.file.path=<path/to/aspsp-adapter-config.csv> \
      -jar xs2a-adapter-app/target/xs2a-adapter-app.jar
    ```

3. After the application has been started you may look over the supported API via [xs2a-adapter swagger page](http://localhost:8999/swagger-ui.html)

#### Containerization

You may also build a docker image and run it in your cloud environment

__Notice:__ Default port is **8081**, and it could be changed in the [Dockerfile](../Dockerfile)

Before executing the next command you should replace the next placeholders with values received at [configuration step](#common-xs2a-adapter-configuration)
- **<path/to/keystore.p12>** with your keystore file location
- **<path/to/adapter.config.properties>** with your adapter config file
- **<path/to/aspsp-adapter-config.csv>** with your aspsp-registry configuration file
- **\<keystore-password>** with a keystore password

```shell script
mvn clean package
docker build -t adorsys/xs2a-adapter:latest .
docker run \
    -v "$(pwd)"/<path/to/keystore.p12>:/pkcs12/key-store.p12 \
    -v "$(pwd)"/<path/to/adapter.config.properties>:/config/adapter.config.properties \
    -v "$(pwd)"/<path/to/aspsp-adapter-config.csv>:/config/aspsp-adapter-config.csv \
    -e JAVA_OPTS="-Xmx1024m -Dcom.sun.security.enableAIAcaIssuers=true -Dpkcs12.keyStore=/pkcs12/key-store.p12 -Dpkcs12.keyStorePassword=<keystore-password>" \
    -e "adapter.config.file.path"="/config/adapter.config.properties" \
    -e "csv.aspsp.adapter.config.file.path"="/config/aspsp-adapter-config.csv" \
    -p 8080:8081 \
    --name xs2a-adapter \
    adorsys/xs2a-adapter:latest
```
After the application has been started you may look over the supported API via [xs2a-adapter swagger page](http://localhost:8080/swagger-ui.html)

### Using XS2A Adapter as a library

XS2A Adapter is available from the Maven-Central repository. To use it in your project, add next dependencies:

```xml
    <dependencies>
    ...
        <dependency>
            <groupId>de.adorsys.xs2a.adapter</groupId>
            <artifactId>adapters</artifactId>
            <version>${xs2a-adapter.version}</version>
            <type>pom</type>
        </dependency>

        <dependency>
            <groupId>de.adorsys.xs2a.adapter</groupId>
            <artifactId>xs2a-adapter-service-loader</artifactId>
            <version>${xs2a-adapter.version}</version>
        </dependency>

        <dependency>
            <groupId>de.adorsys.xs2a.adapter</groupId>
            <artifactId>xs2a-adapter-aspsp-registry</artifactId>
            <version>${xs2a-adapter.version}</version>
        </dependency>
    ...
    </dependencies>

```
`service-loader` provides interfaces for communicating with banks: _AccountInformationService_
and _PaymentInitiationService_ for querying account data and performing payments respectively.
`adapters` contains all implemented bank adapters and `aspsp-registry` provides the Lucene repository
with data records necessary for connecting with German banks. These are records for all implemented
banks at the moment.

If there is no need for using all implemented bank adapters you can replace `adapters` dependency with
a specific one for a concrete adapter.

For example:
```xml
    <dependencies>
    ...
        <dependency>
            <groupId>de.adorsys.xs2a.adapter</groupId>
            <artifactId>adorsys-adapter</artifactId>
            <version>${xs2a-adapter.version}</version>
        </dependency>

    <!--    other XS2A Adapter dependencies    -->
    ...
    </dependencies>
```

Now you will be able to call adapter services to work with banks:
```groovy
// Consent establishing
Response<ConsentsResponse201> consent = accountInformationService.createConsent(requestHeaders,
                                                                                requestParameters,
                                                                                consentsBody);
// retrieving list of Accounts
Response<AccountList> accounts = accountInformationService.getAccountList(requestHeaders,
                                                                          requestParameters);

// Payment Initiation
Response<PaymentInitationRequestResponse201> payment = paymentInitiationService.initiatePayment(paymentService,
                                                                                                paymentProduct,
                                                                                                requestHeaders,
                                                                                                requestParams,
                                                                                                objectBody);
```

With such approach, an [adapter.config.properties](../xs2a-adapter-service-api/src/main/resources/adapter.config.properties)
should be sited in the same module where the Adapter services located.