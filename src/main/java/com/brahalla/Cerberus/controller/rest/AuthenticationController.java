package com.brahalla.Cerberus.controller.rest;

import com.brahalla.Cerberus.configuration.UserAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

  @Autowired
  AuthenticationManager authenticationManager;

  @RequestMapping(method = RequestMethod.POST)
  public String authenticationRequest(@RequestBody String username) throws AuthenticationException {
    UserAuthentication userAuthentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        username,
        username
      )
    );
    return "hello";
  }

}
