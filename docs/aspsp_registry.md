## Managing ASPSP Repository

ASPSP Repository is built upon [Apache Lucene](https://lucene.apache.org/) search software. It is populated from 
[aspsp-adapter-config.csv](xs2a-adapter-aspsp-registry/src/main/resources/aspsp-adapter-config.csv) file.
At the startup Lucene will check if the source CSV was updated and re-populate itself if changes detected.

XS2A Adapter has two interfaces for managing ASPSP Registry: _AspspRepository_ and _AspspReadOnlyRepository_.  

[_AspspRepository_](/xs2a-adapter-service-api/src/main/java/de/adorsys/xs2a/adapter/api/AspspRepository.java) 
allows manipulating Registry with CRUD operations. The user is able to use _LuceneAspspRepository_ 
service, which implements the interface, for creating, retrieving, changing or removing records from the Lucene storage. 

**NOTE**: _AspspRepository_ interface is managing records within Lucene repository only. Source CSV file remains
unmodified.

[_AspspReadOnlyRepository_](/xs2a-adapter-service-api/src/main/java/de/adorsys/xs2a/adapter/api/AspspReadOnlyRepository.java), 
as it can be inferred form the name, allows only various reading operations.

XS2A Adapter Rest layer facilitates only _AspspReadOnlyRepository_, which means there is no API to add, update or delete 
existing records from the box, even though there is an appropriate service. Adapter users are not expected to manipulate 
Lucene storage. All changes should be performed outside the Adapter with a source CSV file and ASPSP Registry should be 
updated only via CSV. 
