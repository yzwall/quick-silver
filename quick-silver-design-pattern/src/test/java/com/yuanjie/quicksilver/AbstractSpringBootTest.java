package com.yuanjie.quicksilver;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@ComponentScan(basePackages = {
    "com.yuanjie.quicksilver.pattern"
})
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@RunWith(SpringRunner.class)
public abstract class AbstractSpringBootTest {
}
