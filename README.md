# service-integration-sdk

Represents a collection of utilities meant to facilitate the implementation
of custom connectors. A connector is an application that handles integration events from the
AppDirect marketplace and takes care of the necessary interaction with an external vendor system in order to 
complete the request.
Essentially a connector is an adapter that allows the marketplace to interact with all external vendor systems in a 
consistent manner.

For more details, see the docs in our [GitHub wiki](https://github.com/AppDirect/service-integration-sdk/wiki)

## Prerequisites
* Your connector application should be implemented using [Spring Boot](https://projects.spring.io/spring-boot/)
* A build tool capable of building and running Spring Boot applications. Although you have several options, in the following
instructions we are going to assume that you're using [Apache Maven](https://maven.apache.org/)

## Features
* Automatic parsing of incoming market integration events
* Automatic oauth authentication of messages using signed fetch
* Provides you with objects modelling the responses needed to be sent to the marketplace, so that you can specify 
what response is sent back to the AppDirect marketplace once the processing of a given integration event is complete
* Automatic Oauth signing of messages to the marketplace

## Sample client application 
An sample connector implemented with the SDK can be found [here](https://github.com/EmilDafinov/chatty-pie-connector)
Please refer to the documentation of the connector for instructions on building / running it.

## Events handled
Using the SDK enables your application to handle the following AppDirect marketplace integration events. You have full control
of the logic that your connector will execute when an integration events. See the [Getting Started](https://github.com/AppDirect/service-integration-sdk/wiki/Getting-Started)
page for more information on how to implement your integration event handlers.

For more information on AppDirect marketplace integration events, and what each one is used for see the AppDirect marketplace documentation
[here](https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events)

Keep in mind that the SDK parses the raw event payloads provided by the AppDirect marketplace (the one described in 
the AppDirect developer documentation above) and creates an event-specific Java object that is specific to the type
of the event. So, as a developer, you would receive a payload containing only the information that is relevant 
to your event type.

For specific information regarding the different event types and the responses that you need to send back to the 
AppDirect marketplace when event processing is complete, see [here](https://github.com/AppDirect/service-integration-sdk/wiki/Event-Descriptions)

## Getting Started
See the [Getting Started](https://github.com/AppDirect/service-integration-sdk/wiki/Getting-Started) instructions
in the wiki for info on how to incorporate the SDK into your application.

## Exposed endpoints
Using the SDK enables several REST points on your connector application. For details, see [here](https://github.com/AppDirect/service-integration-sdk/wiki/Exposed-endpoints)

## Building
* `mvn clean javadoc:jar source:jar install`

## Regular dependencies version update
Use the following to verify that you are using the latest versions of your sdk dependencies
* `mvn versions:update-parent`
* `mvn versions:use-latest-releases`
