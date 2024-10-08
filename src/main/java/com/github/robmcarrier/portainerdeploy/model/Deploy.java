package com.github.robmcarrier.portainerdeploy.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Deploy {
  @Id
  private String id;
  @Indexed(unique = true)
  private int stackId;
  private Map<String, String> env;
  private boolean prune;
  private boolean repositoryAuthentication;
  private boolean pullImage;
}
