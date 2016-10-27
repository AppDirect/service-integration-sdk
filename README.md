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
    <version>[latest-version-of-SDK-goes-here]</version>
</dependency>
```

* Ensure that your application context includes a bean implementing
 the 
 ```
 DeveloperSpecificAppmarketCredentialsSupplier
```
class that returns a pair of valid marketplace credentials

* Ensure that your application context includes a bean implementing
```
AppmarketEventProcessor
```
for every type of market event that you would like your connector to handle.

