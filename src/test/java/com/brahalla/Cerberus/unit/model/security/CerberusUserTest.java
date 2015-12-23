package com.brahalla.Cerberus.unit.model.security;

import com.brahalla.Cerberus.model.security.CerberusUser;

import java.util.Collection;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CerberusUserTest {

  private final Long ID = 1L;
  private final String USERNAME = "username";
  private final String PASSWORD = "password";
  private final Collection<? extends GrantedAuthority> AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList("user,admin");

  @Test
  public void callingCerberusUserConstructorWithoutParametersCreatesExpectedObject() {
    CerberusUser cerberusUser = new CerberusUser();

    assertNull(cerberusUser.getId());
    assertNull(cerberusUser.getUsername());
    assertNull(cerberusUser.getPassword());
    assertNull(cerberusUser.getEmail());
    assertNull(cerberusUser.getAuthorities());
    assertTrue(cerberusUser.getAccountNonExpired());
    assertTrue(cerberusUser.getAccountNonLocked());
    assertTrue(cerberusUser.getCredentialsNonExpired());
    assertTrue(cerberusUser.getEnabled());
  }

  @Test
  public void callingCerberusUserConstructorWithParametersCreatesExpectedObject() {
    CerberusUser cerberusUser = new CerberusUser(ID, USERNAME, PASSWORD, AUTHORITIES);

    assertThat(cerberusUser.getId(), is(ID));
    assertThat(cerberusUser.getUsername(), is(USERNAME));
    assertThat(cerberusUser.getPassword(), is(PASSWORD));
    assertNull(cerberusUser.getEmail());
    assertEquals(cerberusUser.getAuthorities(), AUTHORITIES);
    assertTrue(cerberusUser.getAccountNonExpired());
    assertTrue(cerberusUser.getAccountNonLocked());
    assertTrue(cerberusUser.getCredentialsNonExpired());
    assertTrue(cerberusUser.getEnabled());
  }

}
