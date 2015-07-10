package com.brahalla.Cerberus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      /*.exceptionHandling()
        .and()
      .anonymous()
        .and()
      .servletApi()
        .and()
      .headers()
        .cacheControl()
        .and()*/
      .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
      .csrf()
        .disable();
        //.and()

      // Custom Token based authentication based on the header previously given to the client
      //.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
  }

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
      /*.userDetailsService(userDetailsService())
        .passwordEncoder(new BCryptPasswordEncoder())
        .and()*/
      .inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
  }

}
