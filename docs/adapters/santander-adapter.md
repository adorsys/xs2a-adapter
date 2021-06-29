# Santander Adapter

### Token Configuration

*Mandatory properties:*
- santander.token.consumer_key - consumer key
- santander.token.consumer_secret - consumer secret

*Other properties:*
- santander.token.url - token url (default value is **https://api-sandbox-cc.santander.de/scb-openapis/sx/v1/oauth_matls/token**)
- santander.token.seconds_before_token_expiration - (token.expirationDate - seconds_before_token_expiration) 
it checks before each request. this is time when we'll retrieve new token (default value is **60 seconds**)

### Possible issues:

- as Santander sandbox is static, there is no possibility to authorise consent - it is mentioned in the documentation 
of sandbox that the consent is valid by default (even if its status is RECEIVED);
- Santander sandbox API responds with `400 BAD REQUEST` in case of sending any headers or query params except the mandatory ones. 
This behavior should be checked within the production Santander environment to adjust the further steps.

### Registration for Production Environment

The registration is needed for working with a production environment. A user will want to call a registration endpoint with
a production certificate and a private key and provide a redirect URL:

```
curl --location POST https://api-cc.santander.de/scb-openapis/client/v1/tpp_registrations/mutual_tls
--cert <PATH_TO_YOUR_CERTIFICATE> \
--key <PATH_TO_YOUR_PRIVATE_KEY> \
--header 'Accept: application/json'
--header 'Content-Type: application/json'
--data-raw '{"registeredRedirectUris": [“https://tpp-redirect.com/cb”]}'
```

You can specify multiple redirect URLs.

More details here - https://www.santander.de/privatkunden/specials/api-market/
