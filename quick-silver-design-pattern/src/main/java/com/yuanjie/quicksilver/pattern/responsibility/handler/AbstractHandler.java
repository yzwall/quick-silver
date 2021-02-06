package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Optional;

public abstract class AbstractHandler<P, R> implements InitializingBean {

    private static final Logger staticLogger = LoggerFactory.getLogger(AbstractHandler.class);

    protected Logger logger;

    protected HandlerEnum handlerEnum;

    public final Optional<R> handleTemplate(P context) {
        staticLogger.info("handleTemplate: before handle event, handlerEnum={}", handlerEnum.name());
        Optional<R> result = handle(context);
        staticLogger.info("handleTemplate: after handle event, handlerEnum={}", handlerEnum.name());
        return result;
    }

    public abstract Optional<R> handle(P context);

    public HandlerEnum getHandlerEnum() {
        return handlerEnum;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.logger = LoggerFactory.getLogger(this.getClass());
        Optional.ofNullable(this.getClass().getDeclaredAnnotation(HandlerTag.class))
            .ifPresent(tag -> handlerEnum = tag.value());
    }
}
