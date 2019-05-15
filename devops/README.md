## QWAC  Secret

    oc create secret generic qwac-secret \
    --from-file=key-store=<file>.p12 \
    --from-literal=key-store-password=<password> \
    --from-literal=key-store-type=pkcs12

    oc create secret generic dkb-token \
    --from-literal=consumer-key=<key value> \
    --from-literal=consumer-secret=<secret value>