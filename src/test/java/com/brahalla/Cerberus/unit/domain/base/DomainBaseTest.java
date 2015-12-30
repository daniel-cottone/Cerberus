package com.brahalla.Cerberus.unit.domain.base;

import com.brahalla.Cerberus.domain.base.DomainBase;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DomainBaseTest {

  private String TO_STRING;
  private DomainBase domainBase;

  @Before
  public void setUp() {
    domainBase = new DomainBase();
    TO_STRING = ReflectionToStringBuilder.toString(domainBase);
  }

  @Test
  public void callingDomainBaseToStringReturnsExpectedObject() {
    assertThat(domainBase.toString(), is(TO_STRING));
  }

}
