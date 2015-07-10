package com.brahalla.Cerberus.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Override
  public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = new User(
      "user",
      "user",
      true, true, true, true,
      AuthorityUtils.createAuthorityList("USER")
    );
    if (user == null) {
      throw new UsernameNotFoundException("user not found");
    }
    return user;
  }

}
