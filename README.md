# service-integration-sdk

[![Build Status](https://travis-ci.org/AppDirect/service-integration-sdk.svg?branch=master)](https://travis-ci.org/AppDirect/service-integration-sdk)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.appdirect/service-integration-sdk/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.appdirect/service-integration-sdk)

### Overview
Integrate your application with the AppDirect platform. Create a connector using the Developer SDK and use that connector to connect to the AppDirect ecosystem.

For more details see [GitHub wiki](https://github.com/AppDirect/service-integration-sdk/wiki).

## Prerequisites
* Java 8 or higher
* Build your connector using [Spring Boot 2.0](https://projects.spring.io/spring-boot/)

## Features
* Automatic parsing of incoming AppDirect distribution API calls
* Automatic OAuth authentication for all API events to and from the AppDirect platform
* Provides handlers for your events
* Allows you to use AppMarket as an IdP to sign in to your connector

## Sample client application 
AppDirect developed the Chatty Pie connector as an example of a connector that can be implemented with the SDK. It is a lightweight chat room application. The chatty pie connector was built to handle all AppDirect Distribution API events including creating new customers, developer accounts, assignments, and changes to a subscription. 
To build and run your own connector based on Chatty Pie see [Chatty pie](https://github.com/AppDirect/chatty-pie-connector).

## Getting Started
To incorporate the SDK into your application see [Getting Started](https://github.com/AppDirect/service-integration-sdk/wiki/Getting-Started).

## Event Handling
The SDK enables your application to handle AppDirect marketplace integration events. You have full control
of the logic that your connector will execute when an integration events. See the [Getting Started](https://github.com/AppDirect/service-integration-sdk/wiki/Getting-Started)
page for more information on how to implement your integration event handlers.

For more information about AppDirect marketplace integration events, see the AppDirect Documentation Center
[Subscription event notifications](https://help.appdirect.com/appdistrib/Default.htm#Dev-DistributionGuide/en-subs-event-notifs.html%3FTocPath%3DIntegrate%2520with%2520AppDirect%7CEvent%2520notifications%7CSubscription%2520event%2520notifications%7C_____0/?location%20=%20appdistribution).

The SDK parses the raw event payloads provided by the AppDirect marketplace and creates an event specific Java object that is specific to the type
of event. This ensures you receive a payload containing the information that is relevant to your event type.

For specific information regarding the different event types and the responses that you need to send back to the 
AppDirect marketplace when event processing is complete, see [Event descriptions](https://github.com/AppDirect/service-integration-sdk/wiki/Event-Descriptions).

## Domain association
Some products must be associated with one or multiple domains. For more information see [Domain management](https://github.com/AppDirect/service-integration-sdk/wiki/Domain-management).

## Exposed endpoints
Use the SDK to enable REST points on your connector application. For more information, see [Exposed endpoints](https://github.com/AppDirect/service-integration-sdk/wiki/Exposed-endpoints).

## Building the SDK library locally
* `mvn clean javadoc:jar source:jar install`

