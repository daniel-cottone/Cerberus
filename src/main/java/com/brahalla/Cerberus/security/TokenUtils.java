package com.brahalla.Cerberus.security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {

  @Value("${cerberus.token.secret}")
  private String secret;

  @Value("${cerberus.token.expiration}")
  private long expiration;

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

  private Date getExpirationDate(String token) {
    Date expiration;
    try {
      expiration = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }

  public String generateToken(String subject) {
    return Jwts.builder()
      .setSubject(subject)
      .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }

  public String refreshToken(String token) {
    String refreshedToken;
    try {
      Claims claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
      refreshedToken = this.generateToken(claims.getSubject());
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = this.getUsernameFromToken(token);
    final Date expiration = this.getExpirationDate(token);
    return (username.equals(userDetails.getUsername()) && expiration.after(new Date(System.currentTimeMillis())));
  }

}
