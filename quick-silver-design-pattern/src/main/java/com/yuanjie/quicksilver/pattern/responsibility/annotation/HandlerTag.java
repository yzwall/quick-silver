package com.yuanjie.quicksilver.pattern.responsibility.annotation;

import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerTag {

    HandlerEnum value();
}

