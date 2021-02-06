package com.yuanjie.quicksilver.pattern.responsibility.factory;

import com.google.common.collect.Maps;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HandlerFactory implements InitializingBean {

    @Autowired
    private List<AbstractHandler> handlers;

    private Map<HandlerEnum, AbstractHandler> handlerEnumToHandler = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        handlers.forEach(handler -> handlerEnumToHandler.put(handler.getHandlerEnum(), handler));
    }

    public Optional<AbstractHandler> getAbstractHandler(HandlerEnum handlerEnum) {
        return Optional.ofNullable(handlerEnum).map(handlerEnumToHandler::get);
    }
}
