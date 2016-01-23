package com.brahalla.Cerberus.integration.controller.rest;

import com.brahalla.Cerberus.Application;
import com.brahalla.Cerberus.integration.util.RequestEntityBuilder;
import com.brahalla.Cerberus.integration.util.TestApiConfig;
import com.brahalla.Cerberus.model.json.request.AuthenticationRequest;
import com.brahalla.Cerberus.model.json.response.AuthenticationResponse;
import com.brahalla.Cerberus.security.TokenUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthenticationControllerTest {

  private RestTemplate client;
  private AuthenticationRequest authenticationRequest;
  private String authenticationToken;

  @Value("${cerberus.route.authentication}")
  private String authenticationRoute;

  @Value("${cerberus.route.authentication.refresh}")
  private String refreshRoute;

  @Autowired
  private TokenUtils tokenUtils;

  @Before
  public void setUp() throws Exception {
    client = new RestTemplate();
  }

  @After
  public void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void requestingAuthenticationWithNoCredentialsReturnsBadRequest() throws Exception {
    this.initializeStateForMakingValidAuthenticationRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(authenticationRoute),
        HttpMethod.POST,
        buildAuthenticationRequestEntityWithoutCredentials(),
        Void.class
      );
      fail("Should have returned an HTTP 400: Bad Request status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Bad Request status code");
    }
  }

  @Test
  public void requestingAuthenticationWithInvalidCredentialsReturnsUnauthorized() throws Exception {
    this.initializeStateForMakingInvalidAuthenticationRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(authenticationRoute),
        HttpMethod.POST,
        buildAuthenticationRequestEntity(),
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
  public void requestingProtectedWithValidCredentialsReturnsExpected() throws Exception {
    this.initializeStateForMakingValidAuthenticationRequest();

    ResponseEntity<AuthenticationResponse> responseEntity = client.exchange(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      HttpMethod.POST,
      buildAuthenticationRequestEntity(),
      AuthenticationResponse.class
    );
    AuthenticationResponse authenticationResponse = responseEntity.getBody();

    try {
      assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Ok status code");
    }

    try {
      assertThat(this.tokenUtils.getUsernameFromToken(authenticationResponse.getToken()), is(authenticationRequest.getUsername()));
    } catch (Exception e) {
      fail("Should have returned expected username from token");
    }
  }

  @Test
  public void requestingAuthenticationRefreshWithNoAuthorizationTokenReturnsUnauthorized() throws Exception {
    this.initializeStateForMakingValidAuthenticationRefreshRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(String.format("%s/%s", authenticationRoute, refreshRoute)),
        HttpMethod.GET,
        buildAuthenticationRefreshRequestEntityWithoutAuthorizationToken(),
        Void.class
      );
      fail("Should have returned an HTTP 401: Unauthorized status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    } catch (Exception e) {
      fail("Should have returned an HTTP 401: Unauthorized status code");
    }
  }

  /*@Test
  public void requestingAuthenticationRefreshWithUnauthorizedCredentialsReturnsBadRequest() throws Exception {
    this.initializeStateForMakingInvalidAuthenticationRefreshRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(String.format("%s/%s", authenticationRoute, refreshRoute)),
        HttpMethod.GET,
        buildAuthenticationRefreshRequestEntity(),
        Void.class
      );
      fail("Should have returned an HTTP 400: Bad Request status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Bad Request status code");
    }
  }*/

  @Test
  public void requestingAuthenticationRefreshTokenWithTokenCreatedBeforeLastPasswordResetReturnsBadRequest() throws Exception {
    this.initializeStateForMakingExpiredAuthenticationRefreshRequest();

    try {
      client.exchange(
        TestApiConfig.getAbsolutePath(String.format("%s/%s", authenticationRoute, refreshRoute)),
        HttpMethod.GET,
        buildAuthenticationRefreshRequestEntity(),
        Void.class
      );
      fail("Should have returned an HTTP 400: Bad Request status code");
    } catch (HttpClientErrorException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    } catch (Exception e) {
      fail("Should have returned an HTTP 400: Bad Request status code");
    }
  }

  private void initializeStateForMakingValidAuthenticationRequest() {
    authenticationRequest = TestApiConfig.USER_AUTHENTICATION_REQUEST;
  }

  private void initializeStateForMakingInvalidAuthenticationRequest() {
    authenticationRequest = TestApiConfig.INVALID_AUTHENTICATION_REQUEST;
  }

  private void initializeStateForMakingValidAuthenticationRefreshRequest() {
    authenticationRequest = TestApiConfig.USER_AUTHENTICATION_REQUEST;

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private void initializeStateForMakingInvalidAuthenticationRefreshRequest() {
    authenticationRequest = TestApiConfig.INVALID_AUTHENTICATION_REQUEST;

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private void initializeStateForMakingExpiredAuthenticationRefreshRequest() {
    authenticationRequest = TestApiConfig.EXPIRED_AUTHENTICATION_REQUEST;

    ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
      TestApiConfig.getAbsolutePath(authenticationRoute),
      authenticationRequest,
      AuthenticationResponse.class
    );

    authenticationToken = authenticationResponse.getBody().getToken();
  }

  private HttpEntity<Object> buildAuthenticationRequestEntity() {
    return RequestEntityBuilder.buildRequestEntityWithoutAuthenticationToken(authenticationRequest);
  }

  private HttpEntity<Object> buildAuthenticationRequestEntityWithoutCredentials() {
    return RequestEntityBuilder.buildRequestEntityWithoutBodyOrAuthenticationToken();
  }

  private HttpEntity<Object> buildAuthenticationRefreshRequestEntity() {
    return RequestEntityBuilder.buildRequestEntityWithoutBody(authenticationToken);
  }

  private HttpEntity<Object> buildAuthenticationRefreshRequestEntityWithoutAuthorizationToken() {
    return RequestEntityBuilder.buildRequestEntityWithoutBodyOrAuthenticationToken();
  }

}
