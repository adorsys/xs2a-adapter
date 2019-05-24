.PHONY: all run clean test
DEPENDENCIES = java mvn docker npm newman

all: build-artifacts build-image ## Build all services

## Run section ##
run: all ## Run xs2a-gateway as spring boot app locally
	docker run -d \
		-p 8999:8081 \
		-e adorsys-integ.base_uri=http://xs2a-connector-examples:8089/v1 \
		--network xs2a-sandbox_xs2a-net \
		--name xs2a-gateway \
		adorsys/xs2a-gateway

## Build section ##
build-artifacts: ## Build xs2a-gateway
	mvn -DskipTests clean package

## Build image ##
build-image: ## Build docker image of xs2a-gateway
	docker build . -t adorsys/xs2a-gateway

## Check section ##
check: ## Check required dependencies ("@:" hides nothing to be done for...)
	@: $(foreach exec,$(DEPENDENCIES),\
          $(if $(shell command -v $(exec) 2> /dev/null ),$(info (OK) $(exec) is installed),$(info (FAIL) $(exec) is missing)))

## Clean section ##
clean: clean-java-services ## Clean everything

clean-java-services: ## Clean services temp files
	mvn clean
	docker stop xs2a-gateway
	docker rm xs2a-gateway

## Test section ##
test: postman-scipts ## Run postman scripts

postman-scipts: ## Run postman scripts for adorsys sandbox
	newman run postman/xs2a-gateway-adorsys-sandbox-local.postman_collection.json \
		  -e "postman/xs2a gateway (adorsys-integ).postman_environment.json" \
          --global-var "baseUri=http://localhost:8999" \
          --folder AIS \
          --folder PIS \
          --timeout-request 3000