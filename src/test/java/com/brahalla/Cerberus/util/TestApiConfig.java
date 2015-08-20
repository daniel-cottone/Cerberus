package com.brahalla.Cerberus.util;

public class TestApiConfig {

  public static final String HOSTNAME = "localhost";
  public static final Integer PORT = 9000;

  public static String getAbsolutePath(String relativePath) {
    return String.format("http://%s:%d/api/%s", HOSTNAME, PORT, relativePath);
  }

}
