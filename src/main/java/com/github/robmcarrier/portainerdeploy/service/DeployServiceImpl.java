package com.github.robmcarrier.portainerdeploy.service;

import com.github.robmcarrier.portainerdeploy.configuration.PortainerConfig;
import com.github.robmcarrier.portainerdeploy.model.Auth;
import com.github.robmcarrier.portainerdeploy.model.AuthResponse;
import com.github.robmcarrier.portainerdeploy.model.Deploy;
import com.github.robmcarrier.portainerdeploy.model.DeployDto;
import com.github.robmcarrier.portainerdeploy.model.DeployResponse;
import com.github.robmcarrier.portainerdeploy.repository.DeployRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeployServiceImpl implements DeployService {

  private final DeployRepository deployRepository;
  private final PortainerConfig portainerConfig;
  private final RestClient restClient;

  @Override
  public int triggerDeploy(int stackId) {
    Deploy deploy = deployRepository.getDeployByStackId(stackId);
    if (null == deploy) {
      return -1;
    }
    URI uri = getDeployUri(stackId);
    String token = getToken();

    DeployResponse response = restClient.put()
        .uri(uri)
        .header("Authorization", "Bearer " + token)
        .body(deploy)
        .retrieve()
        .body(DeployResponse.class);

    return response.getStatus();
  }

  @Override
  public int addDeploy(DeployDto deployDto) {
    Deploy deploy = new Deploy();
    deploy.setStackId(deployDto.getStackId());
    deploy.setEnv(deployDto.getEnv());
    deploy.setPrune(deployDto.isPrune());
    deploy.setRepositoryAuthentication(deployDto.isRepositoryAuthentication());
    deploy.setPullImage(deployDto.isPullImage());

    try {
      deployRepository.save(deploy);
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error("Not able to create deploy.");
      if (e.getClass().equals(DuplicateKeyException.class)) {
        return -1;
      }
      return -2;
    }

    return 1;
  }

  private String getToken() {
    URI uri = UriComponentsBuilder.newInstance()
        .scheme("http").host(portainerConfig.getHost())
        .port(portainerConfig.getPort())
        .path("api")
        .path("/auth").build().toUri();
    Auth auth = new Auth();
    auth.setUsername(portainerConfig.getUsername());
    auth.setPassword(portainerConfig.getPassword());
    return restClient.post()
        .uri(uri)
        .body(auth)
        .retrieve()
        .body(AuthResponse.class)
        .getJwt();
  }

  private URI getDeployUri(int stackId) {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("http").host(portainerConfig.getHost()).port(portainerConfig.getPort())
        .path("/api/stacks/").path(
            String.valueOf(stackId)).path("/git/redeploy").queryParam("endpointId", 1).build();
    return uriComponents.toUri();
  }
}
