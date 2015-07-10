package com.brahalla.Cerberus.controller.rest;

import com.brahalla.Cerberus.configuration.TokenAuthenticationService;;
import com.brahalla.Cerberus.configuration.UserAuthentication;
import com.brahalla.Cerberus.model.json.AuthenticationRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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

  @Autowired
  TokenAuthenticationService tokenAuthenticationService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException {
    UserAuthentication authentication = (UserAuthentication) this.authenticationManager.authenticate(
      /*new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),
        authenticationRequest.getPassword()
      )*/
      new UserAuthentication(
        new User(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword(),
          true, true, true, true,
          AuthorityUtils.createAuthorityList("USER")
        )
      )
    );
    this.tokenAuthenticationService.addAuthentication(response, authentication);
    return ResponseEntity.ok(null);
  }

}
