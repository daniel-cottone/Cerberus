package com.brahalla.Cerberus.controller.rest;

import com.brahalla.Cerberus.model.json.AuthenticationRequest;
import com.brahalla.Cerberus.model.json.AuthenticationResponse;

import com.brahalla.Cerberus.security.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${cerberus.route.authentication}")
public class AuthenticationController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

    // Perform the authentication
    Authentication authentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),
        authenticationRequest.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Reload password post-authentication so we can generate token
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    String token = TokenUtils.generateToken(userDetails);

    // Return the token
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

}
