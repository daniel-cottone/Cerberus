package com.brahalla.Cerberus.configuration;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserAuthentication implements Authentication {

  private final User user;
  private boolean authenticated = true;

  public UserAuthentication(User user) {
    this.user = user;
  }

  @Override
  public String getName() {
    return this.user.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.user.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return this.user.getPassword();
  }

  @Override
  public User getDetails() {
    return this.user;
  }

  @Override
  public Object getPrincipal() {
    return this.user.getUsername();
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
}
