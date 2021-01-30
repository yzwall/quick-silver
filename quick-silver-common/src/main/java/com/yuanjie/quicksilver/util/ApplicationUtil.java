package com.yuanjie.quicksilver.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Component
public class ApplicationUtil {

    @Autowired
    private ApplicationContext applicationContext;


    public <T> Optional<T> getBeanByType(Class<T> clazz) {
        return Optional.ofNullable(applicationContext.getBeansOfType(clazz))
            .flatMap(map -> map.values().stream().findFirst());
    }

    public <T> Optional<T> getBeanByName(String beanName, Class<T> clazz) {
        return Optional.ofNullable(beanName).map(name -> applicationContext.getBean(beanName, clazz));
    }
}
