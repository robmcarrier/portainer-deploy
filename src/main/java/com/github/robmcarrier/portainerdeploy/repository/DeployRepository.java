package com.github.robmcarrier.portainerdeploy.repository;

import com.github.robmcarrier.portainerdeploy.model.Deploy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeployRepository extends MongoRepository<Deploy, String> {
  Deploy getDeployByStackId(int stackId);
}
