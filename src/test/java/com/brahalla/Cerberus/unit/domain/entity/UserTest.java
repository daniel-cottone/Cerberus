package com.brahalla.Cerberus.unit.domain.entity;

import com.brahalla.Cerberus.domain.entity.User;

import java.util.Date;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserTest {

  private final String USERNAME = "username";
  private final String PASSWORD = "password";
  private final String EMAIL = "user@domain.tld";
  private final Date LAST_PASSWORD_RESET = new Date();
  private final String AUTHORITIES = "user,admin";

  @Test
  public void callingUserConstructorWithoutParametersCreatesExpectedObject() {
    User user = new User();

    assertNull(user.getUsername());
    assertNull(user.getPassword());
    assertNull(user.getEmail());
    assertNull(user.getLastPasswordReset());
    assertNull(user.getAuthorities());
  }

  @Test
  public void callingUserConstructorWithParametersCreatesExpectedObject() {
    User user = new User(USERNAME, PASSWORD, EMAIL, LAST_PASSWORD_RESET, AUTHORITIES);

    assertThat(user.getUsername(), is(USERNAME));
    assertThat(user.getPassword(), is(PASSWORD));
    assertThat(user.getEmail(), is(EMAIL));
    assertThat(user.getLastPasswordReset(), is(LAST_PASSWORD_RESET));
    assertThat(user.getAuthorities(), is(AUTHORITIES));
  }

  @Test
  public void callingUserGettersAndSettersReturnsExpectedObjects() {
    User user = new User();

    user.setUsername(USERNAME);
    user.setPassword(PASSWORD);
    user.setEmail(EMAIL);
    user.setLastPasswordReset(LAST_PASSWORD_RESET);
    user.setAuthorities(AUTHORITIES);

    assertThat(user.getUsername(), is(USERNAME));
    assertThat(user.getPassword(), is(PASSWORD));
    assertThat(user.getEmail(), is(EMAIL));
    assertThat(user.getLastPasswordReset(), is(LAST_PASSWORD_RESET));
    assertThat(user.getAuthorities(), is(AUTHORITIES));
  }

}
