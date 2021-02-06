package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@HandlerTag(HandlerEnum.LIST_HANDLER_NO_3)
@Component
public class LinkedListThirdHandler extends AbstractLinkedListHandler<Object, String> {

    @Override
    public Optional<String> handle(Object context) {
        logger.info("LinkedListThirdHandler handle finished.");
        return Objects.nonNull(next) ? next.handleTemplate(context) : Optional.empty();
    }
}
