package com.brahalla.Cerberus.security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

  @Value("${cerberus.token.secret}")
  private String secret;

  public String getUsernameFromToken(String token) {
    String username;
    try {
      username = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return (getUsernameFromToken(token).equals(userDetails.getUsername()));
  }

}
