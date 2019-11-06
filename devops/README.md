## Secrets

    oc create secret generic pkcs12-key-store-secret \
    --from-file=key-store.p12=<filename>.p12 \
    --from-literal=key-store-password=<password>

    oc get secrets
    
    oc delete secret <secret name>

## Chart deployment and configuration files
https://git.adorsys.de/adorsys/xs2a-adapter
