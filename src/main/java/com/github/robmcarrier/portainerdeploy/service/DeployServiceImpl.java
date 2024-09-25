package com.github.robmcarrier.portainerdeploy.service;

import com.github.robmcarrier.portainerdeploy.model.Deploy;
import com.github.robmcarrier.portainerdeploy.repository.DeployRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeployServiceImpl implements DeployService{

  private final DeployRepository deployRepository;

  @Override
  public void triggerDeploy(int stackId) {
    Deploy deploy = deployRepository.getDeployByStackId(stackId);
    if (null == deploy) {
      return;
    }



  }


}
