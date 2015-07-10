package com.brahalla.Cerberus.configuration;

import java.util.HashMap;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
  private final HashMap<String, User> userMap = new HashMap<String, User>();

  @Override
  public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = this.userMap.get(username);
    if (user == null) {
      throw new UsernameNotFoundException("user not found");
    }
    this.detailsChecker.check(user);
    return user;
  }

  public void addUser(User user) {
    this.userMap.put(user.getUsername(), user);
  }

}
