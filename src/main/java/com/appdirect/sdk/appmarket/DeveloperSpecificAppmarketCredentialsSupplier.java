package com.appdirect.sdk.appmarket;

import java.util.function.Supplier;

/**
 * Implementations of this interface provide a way of defining an object that the service-integration-sdk
 * can use in order to retrieve the developer credentials. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
public interface DeveloperSpecificAppmarketCredentialsSupplier extends Supplier<DeveloperSpecificAppmarketCredentials> {
}
