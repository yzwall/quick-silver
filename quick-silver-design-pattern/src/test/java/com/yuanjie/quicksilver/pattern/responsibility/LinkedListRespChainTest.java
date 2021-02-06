package com.yuanjie.quicksilver.pattern.responsibility;

import com.yuanjie.quicksilver.AbstractSpringBootTest;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import com.yuanjie.quicksilver.pattern.responsibility.factory.HandlerFactory;
import com.yuanjie.quicksilver.pattern.responsibility.handler.AbstractHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.LinkedListFirstHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.LinkedListSecondHandler;
import com.yuanjie.quicksilver.pattern.responsibility.handler.LinkedListThirdHandler;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkedListRespChainTest extends AbstractSpringBootTest {

    @Autowired
    LinkedListFirstHandler linkedListFirstHandler;

    @Autowired
    LinkedListSecondHandler linkedListSecondHandler;

    @Autowired
    LinkedListThirdHandler linkedListThirdHandler;

    @Autowired
    HandlerFactory handlerFactory;

    @Test
    public void returnExpectationWhenTestRespChainByLinkedList() {

        assertThat(linkedListFirstHandler).isNotNull();
        assertThat(linkedListFirstHandler.getHandlerEnum()).isNotNull().isEqualTo(HandlerEnum.LIST_HANDLER_NO_1);
        assertThat(linkedListSecondHandler).isNotNull();

        // chain of resp implemented by LinkedList config
        List<HandlerEnum> handlerConfigs = Lists.newArrayList(HandlerEnum.LIST_HANDLER_NO_2,
            HandlerEnum.LIST_HANDLER_NO_3,
            HandlerEnum.LIST_HANDLER_NO_1);
        List<AbstractHandler> handlers = handlerConfigs.stream()
            .map(handlerFactory::getAbstractHandler)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        assertThat(handlers).isNotNull().isNotEmpty()
            .containsExactly(linkedListSecondHandler, linkedListThirdHandler, linkedListFirstHandler);

        IRespChain<Object, String> chain = LinkedListRespChain.of();

        // build chain
        handlers.forEach(chain::addHandler);

        // chain of resp implemented by LinkedList execute
        Object context = new Object();
        chain.handle(context);
    }

}