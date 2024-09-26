package com.github.robmcarrier.portainerdeploy.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeployDto {
  private int stackId;
  private Map<String, String> env;
  private boolean prune;
  private boolean repositoryAuthentication;
  private boolean pullImage;
}
