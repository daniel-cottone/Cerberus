package com.brahalla.Cerberus.configuration;

import io.jsonwebtoken.*;

import org.springframework.security.core.userdetails.User;

public class TokenHandler {

  private final String secret;
  private final UserService userService;

  public TokenHandler(String secret, UserService userService) {
    this.secret = secret;
    this.userService = userService;
  }

  public User parseUserFromToken(String token) {
    String username = Jwts.parser()
      .setSigningKey(this.secret)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
    return this.userService.loadUserByUsername(username);
  }

  public String createTokenForUser(User user) {
    return Jwts.builder()
      .setSubject(user.getUsername())
      .signWith(SignatureAlgorithm.HS512, this.secret)
      .compact();
  }

}
