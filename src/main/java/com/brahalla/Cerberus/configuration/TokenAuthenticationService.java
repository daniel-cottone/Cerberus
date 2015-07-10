package com.brahalla.Cerberus.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public class TokenAuthenticationService {

  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
  private final TokenHandler tokenHandler;

  public TokenAuthenticationService(String secret, UserService userService) {
    this.tokenHandler = new TokenHandler(secret, userService);
  }

  public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
    final User user = authentication.getDetails();
    response.addHeader(AUTH_HEADER_NAME, this.tokenHandler.createTokenForUser(user));
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    if (token != null) {
      final User user = this.tokenHandler.parseUserFromToken(token);
      if (user != null) {
        return new UserAuthentication(user);
      }
    }
    return null;
  }

}
