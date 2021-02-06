package com.yuanjie.quicksilver.pattern.responsibility;

import com.yuanjie.quicksilver.AbstractSpringBootTest;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import com.yuanjie.quicksilver.pattern.responsibility.factory.HandlerFactory;
import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.FirstHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.SecondHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.ThirdHandler;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayListRespChainTest extends AbstractSpringBootTest {

    @Autowired
    HandlerFactory handlerFactory;

    @Autowired
    FirstHandler firstHandler;

    @Autowired
    SecondHandler secondHandler;

    @Autowired
    ThirdHandler thirdHandler;

    @Test
    public void returnExpectationWhenTestRespChainByArrayList() {

        // chain of resp implemented by ArrayList config
        List<HandlerEnum> handlerConfigs = Lists.newArrayList(HandlerEnum.HANDLER_NO_2, HandlerEnum.HANDLER_NO_3, HandlerEnum.HANDLER_NO_1);
        List<AbstractHandler> handlers = handlerConfigs.stream()
            .map(handlerFactory::getAbstractHandler)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        assertThat(handlers).isNotNull().isNotEmpty()
            .containsExactly(secondHandler, thirdHandler, firstHandler);

        IRespChain<Object, String> chain = ArrayListRespChain.of();

        // build chain
        handlers.forEach(chain::addHandler);

        // chain of resp implemented by LinkedList execute
        Object context = new Object();
        chain.handle(context);

    }


}