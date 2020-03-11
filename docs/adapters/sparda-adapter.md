# Sparda Adapter

### OAuth flow:

1.Get Authorisation Request:

According to Sparda documentation, there is a list of query params for get authorisation request:
- mandatory:
    - bic
    - client_id
    - redirect_uri
    - scope
    - response_type
    - code_challenge
- optional:
    - state
    - code_challenge_method

The adapter is going to help the TPP with adjusting some of these params:

- bic:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from `aspsp-registry-manager` for the current ASPSP
- client_id:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from the certificate
- redirect_uri:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
- scope:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value `ais` will be used as a default one
- response_type:
    - the value `code` will always be used
- code_challenge:
    - if TPP provides it within the request, the provided value will be used
    - otherwise:
        - if TPP provides valid `code_verifier` value, `code_challenge` will be computed by adapter using the provided `code_verifier` value
        - otherwise:
            - if default `code_verifier` is configured within the `adapters.config.properties`
            (the property to be used is `sparda.oauth_approach.default_code_verifier`),
            `code_challenge` will be computed by adapter using the preconfigured `code_verifier` value
        - please, notice that `code_verifier` has several constraints:
            - minimum length is 43 characters
            - maximum length is 127 characters
            - only word characters, `-`, `.`, `_` and `~` are allowed
            - the regex is `[\w\-\._~]{43,127}`
- code_challenge_method:
    - the only allowed value is `S256`, so either it or nothing will be used (as this param is optional)

2.Get Token Request:

According to Sparda documentation, there is a list of query params for get token request:
- mandatory:
    - grant_type
    - client_id
    - redirect_uri
    - code
    - code_verifier

The adapter is going to help the TPP with adjusting some of these params:

- grant_type:
    - the value `authorization_code` will always be used
- client_id:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from the certificate
- redirect_uri:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
- code:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
- code_verifier:
    - if TPP provides valid `code_verifier` value, the provided value will be used
    - otherwise if default `code_verifier` is configured within the `adapters.config.properties`
      (the property to be used is `sparda.oauth_approach.default_code_verifier`),
      the preconfigured `code_verifier` value will be used
    - please, notice that `code_verifier` has several constraints:
        - minimum length is 43 characters
        - maximum length is 127 characters
        - only word characters, `-`, `.`, `_` and `~` are allowed
        - the regex is `[\w\-\._~]{43,127}`

3.Refresh Token Request:

According to Sparda documentation, there is a list of query params for refresh token request:
- mandatory:
    - grant_type
    - client_id
    - refresh_token

The adapter is going to help the TPP with adjusting some of these params:

- grant_type:
    - the value `refresh_token` will always be used
- client_id:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from the certificate
- refresh_token:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
