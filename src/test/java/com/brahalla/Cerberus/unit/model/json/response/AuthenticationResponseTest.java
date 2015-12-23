package com.brahalla.Cerberus.unit.model.json.response;

import com.brahalla.Cerberus.model.json.response.AuthenticationResponse;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class AuthenticationResponseTest {

  private final String TOKEN = "token";

  @Test
  public void callingAuthenticationResponseConstructorWithoutParametersCreatesExpectedObject() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();

    assertNull(authenticationResponse.getToken());
  }

  @Test
  public void callingAuthenticationResponseConstructorWithParametersCreatesExpectedObject() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse(TOKEN);

    assertThat(authenticationResponse.getToken(), is(TOKEN));
  }

  @Test
  public void callingAuthenticationResponseGettersAndSettersReturnsExpectedObjects() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();

    authenticationResponse.setToken(TOKEN);
    assertThat(authenticationResponse.getToken(), is(TOKEN));
  }

}
