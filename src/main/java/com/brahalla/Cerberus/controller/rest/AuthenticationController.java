package com.brahalla.Cerberus.controller.rest;

import com.brahalla.Cerberus.model.json.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
  public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest ) throws AuthenticationException {
    ResponseEntity<?> response = null;
    Authentication authentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),
        authenticationRequest.getPassword()
      )
    );
    response = ResponseEntity.ok(null);
    return response;
  }

}
