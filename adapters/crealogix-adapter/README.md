## Crealogix-adapter

This adapter covers banks which use a PSD2 API solution provided by [CREALOGIX Group](https://crealogix.com/ch/en/)

Connected CREALOGIX banks:
- DKB

### Configuration

Crealogix solution assumes utilising a set of keys and secrets for accessing their API. Currently, a user of the Adapter
will need to provide in [adapter.config.properties](../../xs2a-adapter-service-api/src/main/resources/adapter.config.properties) 
values for next keys:

*Mandatory DKB properties:*
- **dkb.token.consumer_key** - consumer key, can be found on the DKB developer portal
- **dkb.token.consumer_secret** - consumer secret, given after registering on the DKB developer portal
- **dkb.token.tpp_id** - usually a TPP ID from a certificate, can be found after registering a certificate on the DKB developer portal
- **dkb.token.tpp_secret** - tpp secret, given after registering certificate on the DKB developer portal
