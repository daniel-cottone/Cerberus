package com.brahalla.Cerberus.unit.model.factory;

import com.brahalla.Cerberus.domain.entity.User;
import com.brahalla.Cerberus.model.factory.CerberusUserFactory;
import com.brahalla.Cerberus.model.security.CerberusUser;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CerberusUserFactoryTest {

  private final Long ID = 1L;
  private final String USERNAME = "username";
  private final String PASSWORD = "password";
  private final String EMAIL = "user@domain.tld";
  private final Date LAST_PASSWORD_RESET = new Date();
  private final String AUTHORITIES_STRING = "user,admin";
  private final Collection<? extends GrantedAuthority> AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList(AUTHORITIES_STRING);
  private final Boolean ACCOUNT_NON_EXPIRED = true;
  private final Boolean ACCOUNT_NON_LOCKED = true;
  private final Boolean CREDENTIALS_NON_EXPIRED = true;
  private final Boolean ENABLED = true;

  @Test(expected = NullPointerException.class)
  public void callingCreateWithNullObjectReturnsNullPointerException() {
    User user = null;
    CerberusUser cerberusUser = CerberusUserFactory.create(user);
  }

  @Test
  public void callingCreateWithEmptyUserObjectReturnsExpectedObject() {
    User user = new User();
    CerberusUser cerberusUser = CerberusUserFactory.create(user);

    assertNull(cerberusUser.getId());
    assertNull(cerberusUser.getUsername());
    assertNull(cerberusUser.getPassword());
    assertNull(cerberusUser.getEmail());
    assertNull(cerberusUser.getLastPasswordReset());
    assertNull(cerberusUser.getAuthorities());
    assertTrue(cerberusUser.getAccountNonExpired());
    assertTrue(cerberusUser.getAccountNonLocked());
    assertTrue(cerberusUser.getCredentialsNonExpired());
    assertTrue(cerberusUser.getEnabled());
  }

  @Test
  public void callingCreateWithUserObjectReturnsExpectedObject() {
    User user = new User();
    user.setId(ID);
    user.setUsername(USERNAME);
    user.setPassword(PASSWORD);
    user.setEmail(EMAIL);
    user.setLastPasswordReset(LAST_PASSWORD_RESET);
    user.setAuthorities(AUTHORITIES_STRING);
    CerberusUser cerberusUser = CerberusUserFactory.create(user);

    assertThat(cerberusUser.getId(), is(ID));
    assertThat(cerberusUser.getUsername(), is(USERNAME));
    assertThat(cerberusUser.getPassword(), is(PASSWORD));
    assertThat(cerberusUser.getEmail(), is(EMAIL));
    assertThat(cerberusUser.getLastPasswordReset(), is(LAST_PASSWORD_RESET));
    assertEquals(cerberusUser.getAuthorities(), AUTHORITIES);
    assertThat(cerberusUser.getAccountNonExpired(), is(ACCOUNT_NON_EXPIRED));
    assertThat(cerberusUser.getAccountNonLocked(), is(ACCOUNT_NON_LOCKED));
    assertThat(cerberusUser.getCredentialsNonExpired(), is(CREDENTIALS_NON_EXPIRED));
    assertThat(cerberusUser.getEnabled(), is(ENABLED));
  }

}
