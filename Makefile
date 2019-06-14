.PHONY: run clean test
DEPENDENCIES = java mvn newman

## Run section ##
run: ## Run xs2a-adapter as spring boot app locally
	mvn -DskipTests clean package
	java \
	-Djavax.net.ssl.keyStoreType=pkcs12 \
	-Djavax.net.ssl.keyStore=<certificate-file> \
	-Djavax.net.ssl.keyStorePassword=<certificate-password> \
	-Dcom.sun.security.enableAIAcaIssuers=true \
	-Ddkb.token.consumer_key=<key> \
	-Ddkb.token.consumer_secret=<secret> \
	-jar xs2a-adapter-app/target/xs2a-adapter-app.jar

## Check section ##
check: ## Check required dependencies ("@:" hides nothing to be done for...)
	@: $(foreach exec,$(DEPENDENCIES),\
          $(if $(shell command -v $(exec) 2> /dev/null ),$(info (OK) $(exec) is installed),$(info (FAIL) $(exec) is missing)))

## Clean section ##
clean: ## Clean everything
	mvn clean

## Test section ##
test:  ## Run postman scripts
	newman run postman/xs2a\ adapter.postman_collection.json \
                -d postman/adapters.postman_data.json \
                --globals postman/postman_globals_local.json \
                --folder AIS \
                --folder sepa-credit-transfers \
                --folder pain.001-sepa-credit-transfers \
                --timeout-request 3000
