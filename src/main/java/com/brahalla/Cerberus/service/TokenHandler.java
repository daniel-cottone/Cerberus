package com.brahalla.Cerberus.service;

public class TokenHandler {

  private final String secret;
  private final UserService userService;

  public TokenHandler(String secret, UserService userService) {
    this.secret = secret;
    this.userService = userService;
  }

}
