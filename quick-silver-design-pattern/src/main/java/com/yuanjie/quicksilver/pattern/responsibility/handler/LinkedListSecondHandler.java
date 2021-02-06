package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@HandlerTag(HandlerEnum.LIST_HANDLER_NO_2)
@Component
public class LinkedListSecondHandler extends AbstractLinkedListHandler<Object, String> {

    @Override
    public Optional<String> handle(Object context) {
        logger.info("LinkedListSecondHandler handle finished.");
        return Objects.nonNull(next) ? next.handleTemplate(context) : Optional.empty();
    }
}
