## Secrets

    oc create secret generic key-store-secret \
    --from-file=key-store.p12=example_eidas.p12

    oc get secrets
    
    oc delete secret <secret name>
