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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private EntryPointUnauthorizedHandler unauthorizedHandler;

  private AuthenticationTokenFilter authenticationTokenFilter;

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
      .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf()
        .disable()
      .exceptionHandling()
        .authenticationEntryPoint(this.unauthorizedHandler)
        .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
      //.addFilter(this.authenticationTokenFilter)
      .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .anyRequest().authenticated();

      // Custom Token based authentication based on the header previously given to the client
      //.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("******************\r\n" + username + "******************\r\n");
        if (username != "user") {
          System.out.println("******************\r\n" + "THROWING EXCEPTION\r\n" + "******************\r\n");
          throw new UsernameNotFoundException("no user found with " + username);
        }
        return new User(
          "user",
          "$2a$10$FdLhpmkhswPRCbnw2apRcON2a9Ddax1VjZU.P1bQJW.YHI4owJyfO",
          true, true, true, true,
          AuthorityUtils.createAuthorityList("USER")
        );
      }

    };
  }

}
