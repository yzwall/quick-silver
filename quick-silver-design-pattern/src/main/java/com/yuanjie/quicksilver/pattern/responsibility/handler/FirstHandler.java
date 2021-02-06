package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@HandlerTag(HandlerEnum.HANDLER_NO_1)
@Component
public class FirstHandler extends AbstractHandler<Object, String> {

    @Override
    public Optional<String> handle(Object context) {
        String result = "FirstHandler handle finished.";
        logger.info("FirstHandler handle finished.");
        return Optional.ofNullable(result);
    }
}
