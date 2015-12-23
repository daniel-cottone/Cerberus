package com.brahalla.Cerberus.unit.model.json.request;

import com.brahalla.Cerberus.model.json.request.AuthenticationRequest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class AuthenticationRequestTest {

  private final String USERNAME = "username";
  private final String PASSWORD = "password";

  @Test
  public void callingAuthenticationRequestConstructorWithoutParametersCreatesExpectedObject() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    assertNull(authenticationRequest.getUsername());
    assertNull(authenticationRequest.getPassword());
  }

  @Test
  public void callingAuthenticationRequestConstructorWithParametersCreatesExpectedObject() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(USERNAME, PASSWORD);

    assertThat(authenticationRequest.getUsername(), is(USERNAME));
    assertThat(authenticationRequest.getPassword(), is(PASSWORD));
  }

  @Test
  public void callingAuthenticationRequestGettersAndSettersReturnsExpectedObjects() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    authenticationRequest.setUsername(USERNAME);
    authenticationRequest.setPassword(PASSWORD);

    assertThat(authenticationRequest.getUsername(), is(USERNAME));
    assertThat(authenticationRequest.getPassword(), is(PASSWORD));
  }

}
