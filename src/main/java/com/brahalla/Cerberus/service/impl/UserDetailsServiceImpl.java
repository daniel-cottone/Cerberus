package com.brahalla.Cerberus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (!username.equals("user")) {
      throw new UsernameNotFoundException("no user found with " + username);
    }
    return new User(
      "user",
      this.passwordEncoder.encode("password"),
      true, true, true, true,
      AuthorityUtils.createAuthorityList("USER")
    );
  }

}
