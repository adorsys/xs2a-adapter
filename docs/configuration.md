## XS2A Adapter configuration file
XS2A Adapter already has its own predefined minimum configuration within [adapter.config.properties](xs2a-adapter-service-api/src/main/resources/adapter.config.properties)
file. It is used as a default setup if no other configuration is provided. A user can keep using that config but
will need to add some additional data: 

* to communicate with Bank-Verlag `verlag.apikey.name` and `verlag.apikey.value` must be specified;
* to work with DKB `dkb.token.consumer_key` and `dkb.token.consumer_secret` must be provided, please check out 
this [file](docs/adapters/dkb-adapter.md) for more details;
* `santander.token.consumer_key`, `santander.token.consumer_key` to be filled out for Santander, please check out 
this [file](docs/adapters/santander-adapter.md) for more details;
* ING needs `ing.qwac.alias` and `ing.qseal.alias` with appropriate QWAC and QSEAL from your keystore;

Additionally, a user may provide:

* `sparda.oauth_approach.default_code_verifier` and `sparda.client_id` for Sparda;
* `sparkasse.oauth_approach.default_code_verifier` for Sparkasse;

We already specified a data for connecting with Adorsys Dynamic Sandbox.  
Wiremock mode is turned off by default.

For configuring adapter with your custom settings, just copy [adapter configuration
file](xs2a-adapter-service-api/src/main/resources/adapter.config.properties) and fill out the 
appropriate properties with your values. Then provide the path to your custom config file with `adapter.config.file.path` 
environment variable.
```
# Java property example
-Dadapter.config.file.path=/path/to/your/custom.adapter.config.properties

# Environment variable example
env "adapter.config.file.path=/path/to/your/custom.adapter.config.properties"  perl -le 'print $ENV{"adapter.config.file.path"}'
