.PHONY: all run clean test
DEPENDENCIES = java mvn npm newman

all: build-xs2a-gateway ## Build all services

## Run section ##
run: all ## Run xs2a-gateway as spring boot app locally
	java -jar ./xs2a-gateway-app/target/xs2a-gateway-app.jar

## Build section ##
build-xs2a-gateway: ## Build xs2a-gateway
	mvn -DskipTests clean package

## Check section ##
check: ## Check required dependencies ("@:" hides nothing to be done for...)
	@: $(foreach exec,$(DEPENDENCIES),\
          $(if $(shell command -v $(exec) 2> /dev/null ),$(info (OK) $(exec) is installed),$(info (FAIL) $(exec) is missing)))

## Clean section ##
clean: clean-java-services ## Clean everything

clean-java-services: ## Clean services temp files
	mvn clean

## Test section ##
test: postman-scipts ## Run postman scripts

postman-scipts: ## Run postman scripts for adorsys sandbox
	newman run postman/xs2a-gateway-adorsys-sandbox-local.postman_collection.json \
		  -e "postman/xs2a gateway (adorsys-integ).postman_environment.json" \
          --global-var "baseUri=http://localhost:8999" \
          --folder AIS \
          --folder PIS \
          --timeout-request 3000