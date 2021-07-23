# Santander Adapter

### Adapter Config Properties

*Other properties:*
- santander.token.url - token url (default value is **https://api-sandbox-cc.santander.de/scb-openapis/sx/v1/oauth_matls/token**)

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

### KeyStore

**For Sandbox only**

If you want to request a Santander Sandbox environment, you will need to use bank test certificates only. These certificates 
could be found following the link mentioned above.

Adapter will look for a specific Santander certificate by `santander_qwac` alias in your KeyStore. If none found, it will load
a `default_qwac`.

More about KeyStore [here](../keystore.md)