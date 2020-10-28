## Adapter keystore
Adapter uses keystore (PKCS #12 **java.security.KeyStore**) for holding certificates and keys which are used for signing http messages (QSEAL) and client tls authentication (QWAC).

By default, a keystore must contain two bags named "default_qwac" and "default_qseal" with an empty password.
A key store file may be created using openssl and keytool command line tools.

First create a p12 file for each certificate/key pair with a specific alias.
```
openssl pkcs12 -export -out <p12_file> -in <cert_file> -inkey <key_file> -name <alias>
```
And then combine all p12 files into one.
```
keytool -importkeystore -srckeystore <src_p12> -destkeystore <dest_p12> -srcstorepass '' -deststorepass ''
```
You can specify the path to your keystore with a java vm option **pkcs12.keyStore**. E.g.
```
java -Dpkcs12.keyStore=/path/to/your/keystore.p12 ...
```
