## comdirect-adapter

### How to import self-signed certificate into java keystore
- download certificate from the website https://xs2a-sandbox.comdirect.de/
- import downloaded certificate to the java keystore
```
keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -alias comdirect -storepass [keystore password] -file [downloaded certificate]
```  
