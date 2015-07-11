package com.brahalla.Cerberus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username != "user") {
      throw new UsernameNotFoundException("no user found with " + username);
    }
    return new User(
      "user",
      "password",
      true, true, true, true,
      AuthorityUtils.createAuthorityList("USER")
    );
  }

}
