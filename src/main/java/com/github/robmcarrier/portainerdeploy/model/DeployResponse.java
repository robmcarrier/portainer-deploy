package com.github.robmcarrier.portainerdeploy.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeployResponse {
  private String id;
  private String name;
  private String type;
  private int status;
}
