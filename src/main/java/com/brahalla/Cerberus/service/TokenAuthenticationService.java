package com.brahalla.Cerberus.service;

import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {

  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
  private final TokenHandler tokenHandler;

}
