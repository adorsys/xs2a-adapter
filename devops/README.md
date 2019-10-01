## Secrets

    oc create secret generic qwac-secret \
    --from-file=key-store=<file>.p12 \
    --from-literal=key-store-password=<password> \
    --from-literal=key-store-type=pkcs12

    oc create secret generic key-store-secret \
    --from-file=key-store.p12=example_eidas.p12

    oc get secrets
    
    oc delete secret <secret name>
