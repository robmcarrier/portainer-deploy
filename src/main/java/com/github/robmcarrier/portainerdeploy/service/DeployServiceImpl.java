package com.github.robmcarrier.portainerdeploy.service;

import com.github.robmcarrier.portainerdeploy.configuration.PortainerConfig;
import com.github.robmcarrier.portainerdeploy.model.Auth;
import com.github.robmcarrier.portainerdeploy.model.AuthResponse;
import com.github.robmcarrier.portainerdeploy.model.Deploy;
import com.github.robmcarrier.portainerdeploy.model.DeployResponse;
import com.github.robmcarrier.portainerdeploy.repository.DeployRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
