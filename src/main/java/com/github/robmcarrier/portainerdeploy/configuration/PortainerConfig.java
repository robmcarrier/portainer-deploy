package com.github.robmcarrier.portainerdeploy.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("portainer")
public class PortainerConfig {
  private String host;
  private String port;
  private String username;
  private String password;
}
