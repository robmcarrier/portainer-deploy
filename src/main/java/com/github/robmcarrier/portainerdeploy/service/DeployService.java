package com.github.robmcarrier.portainerdeploy.service;

import com.github.robmcarrier.portainerdeploy.model.DeployDto;

public interface DeployService {
  int triggerDeploy(int stackId);

  int addDeploy(DeployDto deployDto);
}
