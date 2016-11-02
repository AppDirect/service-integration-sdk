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
    <version>1.0-SNAPSHOT</version>
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
  that returns valid appmarket credentials.

* Ensure your application context includes a `AppmarketEventHandler<T>` bean for every type of
  market event your connector handles.
  * Not providing handler for a mandatory event types will lead to an application context failure.
  * Current mandatory events are `SubscriptionOrder` & `SubscriptionCancel`

## Exposed endpoints
* `GET /health`
  Returns a 200 (Success) HTTP code. Used to verify that the server is up

* `GET /api/v1/integration/processEvent?eventUrl=[insert-event-callback-url-here]`
  That is the endpoint where the appmarket sends event notifications

## Building
* `mvn clean javadoc:jar source:jar install`
