## Maven Adapter Archetype

### How to create new adapter
- mvn clean install
- cd ..
- mvn archetype:generate -DarchetypeCatalog=local
- choose this one *local -> de.adorsys.xs2a.adapter:maven-adapter-archetype (maven-adapter-archetype)*
- enter **artifactId** ex. *commerz-bank-adapter*
- enter **bankCode** ex. *10040000*
- enter **baseUri** ex. *https://psd2.api-sandbox.commerzbank.com/berlingroup/v1/*
- enter **classNamePrefix** ex. *CommerzBank*

After these steps adapter module will be generated. 

Happy coding!
