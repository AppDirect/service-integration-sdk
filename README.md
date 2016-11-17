# service-integration-sdk

Represents a collection of utilities meant to facilitate the implementation
of custom connectors. It is assumed that the SDK is consumed by
Spring Boot applications.

## Features
* Automatic parsing of incoming market events
* Automatic oauth authentication of messages using signed fetch
* Automatic Oauth signing of messages to the marketplace

##Getting started
* Include a dependency on the sdk in your pom.xml
```
<dependency>
    <groupId>com.appdirect</groupId>
    <artifactId>service-integration-sdk</artifactId>
    <version>1.4-SNAPSHOT</version>
</dependency>
```

* Ensure your application context imports the `ConnectorSdkConfiguration`
  class; Use the import annotation `@Import(ConnectorSdkConfiguration.class)`. i.e.
```
@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
    // your code...
}
```

* Ensure your application context includes a `DeveloperSpecificAppmarketCredentialsSupplier` bean
  that returns valid appmarket credentials given a consumer key.

* Ensure your application context includes a `AppmarketEventHandler<T>` bean for every type of
  market event your connector handles.
  * Not providing handler for a mandatory event types will lead to an application context failure.
  * Current mandatory events are `SubscriptionOrder`, `SubscriptionCancel` & `SubscriptionChange`

## Exposed endpoints
* `GET /health`
  Returns a 200 (Success) HTTP code. Used to verify that the server is up

* `GET /info`
  Returns information about the deployed application. The information returned varies
  according to the configuration of the client application. Currently we've 
  confirmed that if there is a `git.properties` file in the classpath, generated
  by the [git commit id maven plugin](https://github.com/ktoso/maven-git-commit-id-plugin),
  you'll get output like:
  ```
  {
  	"git": {
  		"commit": {
  			"time": "2016-11-07T18:05:22.000+0000",
  			"id": "2940352"
  		},
  		"branch": "bugfix/HTTPSigInConnector"
  	}
  }
  ```
* `GET /api/v1/integration/processEvent?eventUrl=[insert-event-callback-url-here]`
  That is the endpoint where the appmarket sends event notifications

Note that the SDK includes the `spring-boot-starter-actuator`, which 
means several more endpoints are exposed automatically by Spring.
For more information review the documentation [here](http://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/htmlsingle/#production-ready-endpoints)

## Building
* `mvn clean javadoc:jar source:jar install`
