package com.github.robmcarrier.portainerdeploy.controller;

import com.github.robmcarrier.portainerdeploy.service.DeployService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeployController {

  private final DeployService deployService;

  @PostMapping
  public ResponseEntity<String> triggerDeploy(int stackId) {
    deployService.triggerDeploy(stackId);
    return new ResponseEntity<>("Deploy triggered", HttpStatusCode.valueOf(200));
  }
}
