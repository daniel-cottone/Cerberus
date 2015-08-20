package com.brahalla.Cerberus.suite;

import com.brahalla.Cerberus.integration.controller.rest.ProtectedControllerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ProtectedControllerTest.class
})
public class IntegrationTestSuite {

}
