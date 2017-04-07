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
* Automatic parsing of incoming market events
* Automatic oauth authentication of messages using signed fetch
* Automatic Oauth signing of messages to the marketplace

## Sample client application 
An sample connector implemented with the SDK can be found [here](https://github.com/EmilDafinov/chatty-pie-connector)
Please refer to the documentation of the connector for instructions on building / running it.

## Getting Started
See the [Getting Started](https://github.com/AppDirect/service-integration-sdk/wiki/Getting-Started) instructions
in the wiki for info on how to incorporate the SDK into your application.

## Exposed endpoints
Using the SDK enables several REST points on your connector application. For details, see [here](https://github.com/AppDirect/service-integration-sdk/wiki/Exposed-endpoints)

## Building
* `mvn clean javadoc:jar source:jar install`

## Regular dependencies version update
* `mvn versions:update-parent`
* `mvn versions:use-latest-releases`
