package com.brahalla.Cerberus.security;

import io.jsonwebtoken.*;

import org.springframework.security.core.userdetails.UserDetails;

public final class TokenUtils {

  private final static String secret = "dirtySecret";

  public static String getUsernameFromToken(String token) {
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

  public static String generateToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    return (getUsernameFromToken(token).equals(userDetails.getUsername()));
  }

}
