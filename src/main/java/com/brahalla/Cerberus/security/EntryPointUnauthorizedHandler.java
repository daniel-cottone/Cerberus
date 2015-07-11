package com.brahalla.Cerberus.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
  }

}
