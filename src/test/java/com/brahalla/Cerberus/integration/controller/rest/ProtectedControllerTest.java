package com.brahalla.Cerberus.integration.controller.rest;

import com.brahalla.Cerberus.Application;
import com.brahalla.Cerberus.model.json.AuthenticationRequest;
import com.brahalla.Cerberus.model.json.AuthenticationResponse;
import com.brahalla.Cerberus.util.TestApiRoutes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ProtectedControllerTest {

  private RestTemplate client;
  private AuthenticationRequest authenticationRequest;
  private String authenticationToken;

  @Before
  public void setUp() throws Exception {
    client = new RestTemplate();
  }

  @After
  public void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void requestingProtectedWithNoAuthorizationTokenReturnsForbidden() throws Exception {
    this.initializeStateForMakingValidProtectedRequest();

    try {
      client.exchange(TestApiRoutes.PROTECTED_ROUTE, HttpMethod.GET, buildProtectedRequestEntityWithoutAuthorizationToken(), Void.class);
      fail("Should have returned an HTTP 401: Unauthorized status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    } catch (Exception e) {
      fail("Should have returned an HTTP 401: Unauthorized status code");
    }
  }

  @Test
  public void requestingProtectedWithValidCredentialsReturnsExpectedResult() throws Exception {
    this.initializeStateForMakingValidProtectedRequest();

    try {
      ResponseEntity<String> responseEntity = client.exchange(
        TestApiRoutes.PROTECTED_ROUTE,
        HttpMethod.GET,
        buildProtectedRequestEntity(),
        String.class
      );
      assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Ok status code");
    }
  }

  private void initializeStateForMakingValidProtectedRequest() {
    authenticationRequest = new AuthenticationRequest("user", "password");

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiRoutes.AUTHENTICATION_ROUTE,
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private HttpEntity<String> buildProtectedRequestEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Auth-Token", authenticationToken);
    headers.add("Content-Type", "application/json");
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    return entity;
  }

  private HttpEntity<String> buildProtectedRequestEntityWithoutAuthorizationToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    return entity;
  }

}
