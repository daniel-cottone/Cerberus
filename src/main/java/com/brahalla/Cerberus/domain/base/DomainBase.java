package com.brahalla.Cerberus.domain.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class DomainBase implements Serializable {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
