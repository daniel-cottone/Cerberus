package com.brahalla.Cerberus.integration.config;

import com.brahalla.Cerberus.integration.util.TestApiConfig;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
    factory.setPort(TestApiConfig.PORT);
    factory.setSessionTimeout(10, TimeUnit.MINUTES);
    return factory;
  }

}
