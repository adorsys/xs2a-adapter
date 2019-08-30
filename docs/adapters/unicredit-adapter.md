# UniCredit Adapter

### Description

UniCredit PSD2 APIs is based on the Berlin Group specification v1.1 (according to the documentation on https://developer.unicredit.eu/).

As `xs2a-adapter` application has been build upon the Berlin Group specification v1.3 and versioning is out of the scope of the short-term tasks,
it is important to provide a mapping between v1.1 and v1.3 to make it work.

### Differences:

1)the main difference between UniCredit PSD2 APIs and Berlin Group v1.3 is in the authorisation process:

- there is no such a model as `ScaStatus` in UniCredit PSD2 APIs;
- there are no start authorisation endpoint in UniCredit PSD2 APIs (like `POST /consents/{consentId}/authorisations` (AIS) 
or `POST /{payment-service}/{payment-product}/{paymentId}/authorisations` (PIS) in BG 1.3), 
so we have to redirect this call to `PUT /consents/{consentId}` (AIS) 
or `PUT /{payment-service}/{payment-product}/{paymentId}` inside the `unicredit-adapter`;
- update PSU data endpoints look different in UniCredit PSD2 APIs: `PUT /consents/{consentId}` (AIS) 
or `PUT /{payment-service}/{payment-product}/{paymentId}` (PIS) 
instead of `PUT /consents/{consentId}/authorisations/{authorisationId}` (AIS) 
or `PUT /{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}` (PIS) as in BG 1.3, 
so the call is redirected under the hood inside the `unicredit-adapter`;
- there are no such data as `authorisationId` and `ScaStatus`. `GET SCA status` endpoint doesn't exist as well 
(`GET /consents/{consentId}/authorisations/{authorisationId}` (AIS) or `GET /{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}` (PIS) in BG 1.3), 
so to make this endpoint work we have to make a call to get consent/transaction status endpoints and map the `ConsentStatus`/`TransactionStatus` to `ScaStatus`.
As these statuses are not fully the same, we can not guaranty the full compatibility and data correctness;
- `update PSU data` endpoints return `ConsentStatus` (AIS) and `TransactionStatus` (PIS) in UniCredit PSD2 APIs instead of `ScaStatus`, as required by BG 1.3.
To fix this we return `ScaStatus` based on the response body: for instance, if the response body contains `chosenScaMethod` property, it means that `ScaStatus` is `SCAMETHODSELECTED`;
- UniCredit PSD2 APIs authorisation endpoints responses contain links, that are not expected according to BG 1.3, like `authoriseTransaction` (for the consent creation stage) or 
`next` as the link, that should be used to authorize the transaction with TAN. It requires proper link mapping inside the `unicredit-adapter`;
- transaction authorisation link contains query param `authenticationCurrentNumber`, that is not presented in the BG 1.3.
This param value is used as an authorisation id to return it inside the response from `xs2a-adapter`.

2)issue with `ChallengeData` model in UniCredit PSD2 APIs: there are typos in this model properties: 

- `optMaxLength` instead of `otpMaxLength`;
- `optFormat` instead of `otpFormat`.

So, we have to map it through the custom model `UnicreditChallengeData`.

3)some of AIS `GET` endpoints of UniCredit PSD2 APIs require `PSU-IP-Address` header as a mandatory one.

### Possible issues:

1)as it has been mentioned, there are 2 approaches of getting the missing `ScaStatus`: 
- for `GET SCA status` endpoints: by mapping it from the `ConsentStatus`/`TransactionStatus`;
- for `Update PSU data` endpoints: by getting the status from the content of the response body.
It leads to data non-consistency, as the result of these 2 mappings will be different for the same consent/transaction.
Please, take it into the account and don't rely on `ScaStatus` value whenever possible!

2)as the UniCredit PSD2 APIs documentation provide the test user with the only 1 SCA method available, 
it means that there is no possibility to test the entire Embedded authorisation flow (0 and multiple SCA methods cases);

3)as there is no separate model for authorisation, the implicit authorisation start is not possible at all;

4)for now only Embedded approach is fully supported through the `xs2a-adapter` for UniCredit PSD2 APIs.
