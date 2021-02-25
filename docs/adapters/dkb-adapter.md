# DKB Adapter

### Token Configurations

*Mandatory properties:*
- **dkb.token.consumer_key** - consumer key, can be found on DKB developer portal
- **dkb.token.consumer_secret** - consumer secret, given after registering on DKB developer portal
- **dkb.token.tpp_id** - usually a TPP ID from a certificate, can be found after registering a certificate on DKB developer portal
- **dkb.token.tpp_secret** - tpp secret, given after registering certificate on DKB developer portal

*Other properties:*
- **dkb.token.url** - token url (default value is **https://api.dkb.de/token**)
- **dkb.token.seconds_before_token_expiration** - (token.expirationDate - seconds_before_token_expiration) 
it checks before each request. this is time when we'll retrieve new token (default value is **60 seconds**)
