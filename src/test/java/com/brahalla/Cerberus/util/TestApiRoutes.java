package com.brahalla.Cerberus.util;

public class TestApiRoutes {
  public static final String BASE_ROUTE = "http://localhost:9000/api";
  public static final String AUTHENTICATION_ROUTE = String.format("%s/auth", BASE_ROUTE);
  public static final String PROTECTED_ROUTE = String.format("%s/protected", BASE_ROUTE);
}
