## Crealogix-adapter

This adapter covers banks which use a PSD2 API solution provided by [CREALOGIX Group](https://crealogix.com/ch/en/)

Connected CREALOGIX banks:
- DKB

### Configuration

Currently, a user of the Adapter will need to provide in [adapter.config.properties](../../xs2a-adapter-service-api/src/main/resources/adapter.config.properties) 
values for next keys:

*Mandatory DKB properties:*
- **dkb.psd2_token.url** - URL for obtaining PSD2 Authorisation Token, a default value is provided.
