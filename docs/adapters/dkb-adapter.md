# DKB Adapter

### Token Configuration

*Mandatory properties:*
- dkb.token.consumer_key - consumer key
- dkb.token.consumer_secret - consumer secret

*Other properties:*
- dkb.token.url - token url (default value is **https://api.dkb.de/token**)
- dkb.token.seconds_before_token_expiration - (token.expirationDate - seconds_before_token_expiration) 
it checks before each request. this is time when we'll retrieve new token (default value is **60 seconds**)
