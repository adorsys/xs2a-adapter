# Sparkasse Adapter

### OAuth flow:

1.Get Authorisation Request:

According to Sparkasse documentation, there is a list of query params for get authorisation request:
- mandatory:
    - responseType
    - clientId
    - scope
    - state
    - code_challenge
    - code_challenge_method

The adapter is going to help the TPP with adjusting some of these params:

- responseType:
    - the value `code` will always be used
- clientId:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from the certificate
- scope:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value `ais` will be used as a default one
- state:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
- code_challenge:
    - if TPP provides it within the request, the provided value will be used
    - otherwise:
        - if TPP provides valid `code_verifier` value, `code_challenge` will be computed by adapter using the provided `code_verifier` value
        - otherwise:
            - if default `code_verifier` is configured within the `adapters.config.properties`
            (the property to be used is `sparkasse.oauth_approach.default_code_verifier`),
            `code_challenge` will be computed by adapter using the preconfigured `code_verifier` value
        - please, notice that `code_verifier` has several constraints:
            - minimum length is 44 characters
            - maximum length is 127 characters
            - only word characters, `-`, `.`, `_` and `~` are allowed
            - the regex is `[\w\-\._~]{44,127}`
- code_challenge_method:
    - the only allowed value is `S256`, so it will be used

Please, note that there is a custom naming convention for some params in `Get Authorisation Request`:
- `client_id` and `response_type` should be named in camel case style - `clientId` and `responseType`

For `Get Token Request` naming convention is a usual one (with underscores as separators).

2.Get Token Request:

According to Sparkasse documentation, there is a list of query params for get token request:
- mandatory:
    - code
    - client_id
    - code_verifier
    - grant_type

The adapter is going to help the TPP with adjusting some of these params:

- code:
    - only the value provided by TPP will be used, as there is no way for the adapter to adjust it
- client_id:
    - if TPP provides it within the request, the provided value will be used
    - otherwise, the value will be taken from the certificate
- code_verifier:
    - if TPP provides valid `code_verifier` value, the provided value will be used
    - otherwise if default `code_verifier` is configured within the `adapters.config.properties`
      (the property to be used is `sparkasse.oauth_approach.default_code_verifier`),
      the preconfigured `code_verifier` value will be used
    - please, notice that `code_verifier` has several constraints:
        - minimum length is 44 characters
        - maximum length is 127 characters
        - only word characters, `-`, `.`, `_` and `~` are allowed
        - the regex is `[\w\-\._~]{44,127}`
- grant_type:
    - the value `authorization_code` will always be used
