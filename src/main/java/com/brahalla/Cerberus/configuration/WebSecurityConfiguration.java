package com.brahalla.Cerberus.configuration;

import com.brahalla.Cerberus.service.TokenAuthenticationService;
import com.brahalla.Cerberus.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private final UserService userService;

  @Autowired
  private final TokenAuthenticationService tokenAuthenticationService;

}
