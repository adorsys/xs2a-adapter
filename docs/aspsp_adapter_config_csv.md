## ASPSP Adapter Configuration file

[aspsp-adapter-config.csv](../xs2a-adapter-aspsp-registry/src/main/resources/aspsp-adapter-config.csv) is a CSV document 
that contains all necessary data for XS2A Adapter to communicate with banks. It has next structure (begging from the left column, 
sequentially):

|Column|Condition|Description|
|------|---------|-----------|
|ID|Optional|UUID v4, if no ID provided it will be generated automatically.|
|ASPSP Name|Mandatory|ASPSP Legal name or Bank Name.|
|BIC|Mandatory unless Bank Code is present|Bank Identifier Code. The BIC is the same as the bank's SWIFT address.|
|URL|Mandatory|The URL that XS2A Adapter uses for querying bank APIs.|
|Bank Code|Mandatory unless BIC is present|A bank code is a code assigned by a central bank, a bank supervisory body or a Bankers Association in a country to all its licensed member banks or financial institutions ([Wikipedia](https://en.wikipedia.org/wiki/Bank_code#:~:text=A%20bank%20code%20is%20a,member%20banks%20or%20financial%20institutions.&text=The%20term%20%22bank%20code%22%20is,printed%20on%20a%20credit%20card.)).|
|Adapter ID|Mandatory|The name of an Adapter that must be used for communicating with a bank. E.g. `adorsys-adapter`|
|IDP URL|Optional|An Identity Provider URL, usually is used with OAUTH Approach and is a resource for obtaining Access Token|
|ASPSP SCA Approaches|Optional|The list of supported SCA Approaches by ASPSP. Options are EMBEDDED, REDIRECT, DECOUPLED, OAUTH. ASPSP can have multiple or all Approaches.|

This CSV file is used as a source for the [Adapter ASPSP Registry](aspsp_registry.md). 