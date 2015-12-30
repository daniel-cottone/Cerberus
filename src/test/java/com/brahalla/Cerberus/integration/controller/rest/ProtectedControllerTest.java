package com.brahalla.Cerberus.integration.controller.rest;

import com.brahalla.Cerberus.Application;
import com.brahalla.Cerberus.integration.util.RequestEntityBuilder;
import com.brahalla.Cerberus.integration.util.TestApiConfig;
import com.brahalla.Cerberus.model.json.request.AuthenticationRequest;
import com.brahalla.Cerberus.model.json.response.AuthenticationResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
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

  @Value("${cerberus.route.authentication}")
  private String authenticationRoute;

  @Value("${cerberus.route.protected}")
  private String protectedRoute;

  @Before
  public void setUp() throws Exception {
    client = new RestTemplate();
  }

  @After
  public void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void requestingProtectedWithNoAuthorizationTokenReturnsUnauthorized() throws Exception {
    this.initializeStateForMakingValidProtectedRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(protectedRoute),
        HttpMethod.GET,
        buildProtectedRequestEntityWithoutAuthorizationToken(),
        Void.class
      );
      fail("Should have returned an HTTP 401: Unauthorized status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    } catch (Exception e) {
      fail("Should have returned an HTTP 401: Unauthorized status code");
    }
  }

  @Test
  public void requestingProtectedWithUnauthorizedCredentialsReturnsForbidden() throws Exception {
    this.initializeStateForMakingInvalidProtectedRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(protectedRoute),
        HttpMethod.GET,
        buildProtectedRequestEntity(),
        Void.class
      );
      fail("Should have returned an HTTP 403: Forbidden status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.FORBIDDEN));
    } catch (Exception e) {
      fail("Should have returned an HTTP 403: Forbidden status code");
    }
  }

  @Test
  public void requestingProtectedWithValidCredentialsReturnsExpected() throws Exception {
    this.initializeStateForMakingValidProtectedRequest();

    ResponseEntity<String> responseEntity = client.exchange(
      TestApiConfig.getAbsolutePath(protectedRoute),
      HttpMethod.GET,
      buildProtectedRequestEntity(),
      String.class
    );
    String protectedResponse = responseEntity.getBody();

    try {
      assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Ok status code");
    }

    try {
      assertThat(protectedResponse, is(":O"));
    } catch (Exception e) {
      fail("Should have returned expected response: :O");
    }
  }

  private void initializeStateForMakingValidProtectedRequest() {
    authenticationRequest = TestApiConfig.ADMIN_AUTHENTICATION_REQUEST;

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private void initializeStateForMakingInvalidProtectedRequest() {
    authenticationRequest = TestApiConfig.USER_AUTHENTICATION_REQUEST;

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private HttpEntity<Object> buildProtectedRequestEntity() {
    return RequestEntityBuilder.buildRequestEntityWithoutBody(authenticationToken);
  }

  private HttpEntity<Object> buildProtectedRequestEntityWithoutAuthorizationToken() {
    return RequestEntityBuilder.buildRequestEntityWithoutBodyOrAuthenticationToken();
  }

}
