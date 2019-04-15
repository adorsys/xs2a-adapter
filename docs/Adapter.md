# How to write a xs2a gateway adapter

To create your own adapter you need to implement 4 interfaces and create 2 one-line files.

### Steps:
- Add **xs2a-gateway-api** dependency to your classpath
- Implement **AccountInformationServiceProvider** and **PaymentInitiationServiceProvider** interfaces. Don't forget to provide **correct bank code** otherwise no-one will be able to communicate with your adapter. 
- Provide implementation of **AccountInformationService** interface.
- Provide implementation of **PaymentInitiationService** interface.
- Create **META-INF/services** package inside the resources folder
- Create file with name **de.adorsys.xs2a.gateway.service.provider.AccountInformationServiceProvider**. It should contain the name of your implementation of **AccountInformationServiceProvider**
- Create file with name **de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider**. It should contain the name of your implementation of **PaymentInitiationServiceProvider**

