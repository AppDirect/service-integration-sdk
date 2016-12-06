# service-integration-sdk

Represents a collection of utilities meant to facilitate the implementation
of custom connectors. It is assumed that the SDK is consumed by
Spring Boot applications.

## Features
* Automatic parsing of incoming market events
* Automatic oauth authentication of messages using signed fetch
* Automatic Oauth signing of messages to the marketplace

##Documentation
For detailed docs, check out our [GitHub wiki](https://github.com/AppDirect/service-integration-sdk/wiki) 

##Getting started
* Include a dependency on the sdk in your pom.xml
```
<dependency>
    <groupId>com.appdirect</groupId>
    <artifactId>service-integration-sdk</artifactId>
    <version>1.7-SNAPSHOT</version>
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

* Ensure your application context includes a `AppmarketEventHandler<T>` bean for every type of market events.
  * Not providing handler for a mandatory event types will lead to an application context failure.
  * The events you need to expose `AppmarketEventHandler`s for are
      * [`SubscriptionOrder`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionOrder.java)
      * [`SubscriptionCancel`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionCancel.java)
      * [`SubscriptionChange`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionChange.java)
      * [`SubscriptionClosed`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionClosed.java)
      * [`SubscriptionDeactivated`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionDeactivated.java)
      * [`SubscriptionReactivated`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionReactivated.java)
      * [`SubscriptionUpcomingInvoice`](src/main/java/com/appdirect/sdk/appmarket/events/SubscriptionUpcomingInvoice.java)

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
For more information review the documentation [here](http://docs.spring.io/spring-boot/docs/1.4.2.RELEASE/reference/htmlsingle/#production-ready-endpoints)

## Building
* `mvn clean javadoc:jar source:jar install`

## Regular dependencies version update
* `mvn versions:update-parent`
* `mvn versions:use-latest-releases`
