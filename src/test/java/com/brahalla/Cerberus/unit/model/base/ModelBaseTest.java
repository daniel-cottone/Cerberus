package com.brahalla.Cerberus.unit.model.base;

import com.brahalla.Cerberus.model.base.ModelBase;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ModelBaseTest {

  private String TO_STRING;
  private ModelBase modelBase;

  @Before
  public void setUp() {
    modelBase = new ModelBase();
    TO_STRING = ReflectionToStringBuilder.toString(modelBase);
  }

  @Test
  public void callingModelBaseToStringReturnsExpectedObject() {
    assertThat(modelBase.toString(), is(TO_STRING));
  }

}
