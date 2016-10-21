package com.appdirect.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.appdirect.isv.IsvModule;

@ComponentScan(basePackageClasses = {
    IsvModule.class,
    IsvController.class
})
@SpringBootApplication
class ConnectorApplication {
}
