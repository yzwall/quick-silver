package com.yuanjie.quicksilver.annotation;

import com.yuanjie.quicksilver.service.IKeyGenerator;
import com.yuanjie.quicksilver.service.IResultChecker;
import com.yuanjie.quicksilver.service.impl.DefaultKeyGenerator;
import com.yuanjie.quicksilver.service.impl.DefaultResultChecker;

import java.lang.annotation.*;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DiseableApiCache {

    String apiName() default "";

    Class<? extends IKeyGenerator> keyGenerator() default DefaultKeyGenerator.class;
}
