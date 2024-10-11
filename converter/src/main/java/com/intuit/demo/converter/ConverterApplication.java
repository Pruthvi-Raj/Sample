package com.intuit.demo.converter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class ConverterApplication {
  
  public static void main(String[] args) throws UnknownHostException {
    try {
      SpringApplication springApplication = new SpringApplication(ConverterApplication.class);
      Environment environment = springApplication.run(args).getEnvironment();
      String protocol = "http";
      if (environment.getProperty("server.ssl.key-store") != null) {
        protocol = "https";
      }
      log.info("Server running on: Local:  {}://localhost:{}/swagger-ui.html", protocol,
          environment.getProperty("server.port"));
      log.info("Server running on: External: {}://{}:{}/swagger-ui.html", protocol,
          InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
      log.info("Server running on: Profile: {};",
          environment.getActiveProfiles());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
  
}
