package com.github.robmcarrier.portainerdeploy.controller;

import com.github.robmcarrier.portainerdeploy.model.DeployDto;
import com.github.robmcarrier.portainerdeploy.service.DeployService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeployController {

  private final DeployService deployService;

  @PostMapping
  public ResponseEntity<String> triggerDeploy(int stackId) {
    int status = deployService.triggerDeploy(stackId);
    return new ResponseEntity<>("Deploy triggered; status: " + status, HttpStatusCode.valueOf(200));
  }

  @PostMapping("AddDeploy")
  public ResponseEntity<String> addDeploy(@RequestBody DeployDto deployDto) {
    return null;
  }
}
