package com.github.robmcarrier.portainerdeploy.controller;

import com.github.robmcarrier.portainerdeploy.model.DeployDto;
import com.github.robmcarrier.portainerdeploy.service.DeployService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    return new ResponseEntity<>("Deploy triggered; status: " + status, HttpStatus.ACCEPTED);
  }

  @PostMapping("addDeploy")
  public ResponseEntity<String> addDeploy(@RequestBody DeployDto deployDto) {
    int status = deployService.addDeploy(deployDto);
    switch (status) {
      case 1 -> {
        return new ResponseEntity<>("Deploy info added", HttpStatus.CREATED);
      }
      case -1 -> {
        return new ResponseEntity<>("Deploy info with stack id " + deployDto.getStackId() + " already exists.", HttpStatus.BAD_REQUEST);
      }
      case -2 -> {
        return new ResponseEntity<>("Unable to add deploy info", HttpStatus.SERVICE_UNAVAILABLE);
      }
      default -> throw new IllegalStateException("Unexpected value: " + status);
    }
  }
}
