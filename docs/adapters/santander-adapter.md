# Santander Adapter

### Token Configuration

*Mandatory properties:*
- santander.token.consumer_key - consumer key
- santander.token.consumer_secret - consumer secret

*Other properties:*
- santander.token.url - token url (default value is **https://apigateway-sandbox.api.santander.de/scb-openapis/sx/oauthsos/password/token**)
- santander.token.seconds_before_token_expiration - (token.expirationDate - seconds_before_token_expiration) 
it checks before each request. this is time when we'll retrieve new token (default value is **60 seconds**)

### Possible issues:

- as Santander sandbox is static, there is no possibility to authorise consent - it is mentioned in the documentation of sandbox that the consent is valid by default (even if its status is RECEIVED);
- Santander sandbox API responds with `400 BAD REQUEST` in case of sending any headers or query params except the mandatory ones. 
This behavior should be checked within the production Santander environment to adjust the further steps.
