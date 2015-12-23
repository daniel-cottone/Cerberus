package com.brahalla.Cerberus.unit.domain.entity;

import com.brahalla.Cerberus.domain.entity.User;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserTest {

  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String AUTHORITIES = "user,admin";

  @Test
  public void callingUserConstructorWithoutParametersCreatesExpectedObject() {
    User user = new User();

    assertNull(user.getUsername());
    assertNull(user.getPassword());
    assertNull(user.getAuthorities());
  }

  @Test
  public void callingUserConstructorWithParametersCreatesExpectedObject() {
    User user = new User(USERNAME, PASSWORD, AUTHORITIES);

    assertThat(user.getUsername(), is(USERNAME));
    assertThat(user.getPassword(), is(PASSWORD));
    assertThat(user.getAuthorities(), is(AUTHORITIES));
  }

}
