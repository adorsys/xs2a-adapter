## Crealogix-adapter

This adapter covers banks which use a PSD2 API solution provided by [CREALOGIX Group](https://crealogix.com/ch/en/)

Connected CREALOGIX banks:
- DKB

### Configuration

Currently, a user of the Adapter will need to provide in [adapter.config.properties](../../xs2a-adapter-service-api/src/main/resources/adapter.config.properties) 
values for next keys:

*Mandatory DKB properties:*
- **dkb.psd2_token.url** - URL for obtaining PSD2 Authorisation Token, a default value is provided.

### Registration

To register a TPP a user will need to call any Crealogix XS2A API with a user production certificate and a private key.
This call will create a TPP record within ASPSP database. For example with DKB, a user can make such Create Consent Request:

```
curl --location POST 'https://api.dkb.de/psd2/v1/consents' \
--cert $CERT_PATH \
--key $PR_KEY_PATH \
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