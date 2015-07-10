package com.brahalla.Cerberus.controller.rest;

import com.brahalla.Cerberus.configuration.TokenAuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
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
  TokenAuthenticationService tokenAuthenticationService;

  @RequestMapping(method = RequestMethod.POST)
  public String attemptAuthentication(@RequestBody String username) {
    return "hello";
  }

}
