## Feign client for xs2a-adapter

### How to use

- configure xs2a-adapter URL
```
xs2a-adapter:
  url: http://localhost:8999
```
- add client `xs2a-adapter-service-remote` into your application dependencies
- enable feign clients in your sprintBoot application with next annotation `@EnableFeignClients(basePackages = "de.adorsys.xs2a.adapter.api.remote")`
- configure converters. See example in the test sources `de.adorsys.xs2a.tpp.WebMvcConfig`
