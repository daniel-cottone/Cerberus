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
  private Long expiration;

  public String getUsernameFromToken(String token) {
    String username;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      expiration = claims.getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  private Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
        .setSigningKey(this.secret)
        .parseClaimsJws(token)
        .getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + this.expiration * 1000);
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .setExpiration(this.generateExpirationDate())
      .signWith(SignatureAlgorithm.HS512, this.secret)
      .compact();
  }

  public String generateToken(String subject) {
    return Jwts.builder()
      .setSubject(subject)
      .setExpiration(this.generateExpirationDate())
      .signWith(SignatureAlgorithm.HS512, this.secret)
      .compact();
  }

  public String refreshToken(String token) {
    String refreshedToken;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      refreshedToken = this.generateToken(claims.getSubject());
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = this.getUsernameFromToken(token);
    final Date expiration = this.getExpirationDateFromToken(token);
    return (username.equals(userDetails.getUsername()) && expiration.after(new Date(System.currentTimeMillis())));
  }

}
