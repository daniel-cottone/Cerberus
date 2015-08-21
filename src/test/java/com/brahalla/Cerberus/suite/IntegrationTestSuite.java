package com.brahalla.Cerberus.suite;

import com.brahalla.Cerberus.integration.controller.rest.AuthenticationControllerTest;
import com.brahalla.Cerberus.integration.controller.rest.ProtectedControllerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AuthenticationControllerTest.class,
  ProtectedControllerTest.class
})
public class IntegrationTestSuite {

}
