package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@HandlerTag(HandlerEnum.HANDLER_NO_3)
@Component
public class ThirdHandler extends AbstractHandler<Object, String> {

    @Override
    public Optional<String> handle(Object context) {
        String result = "ThirdHandler handle finished.";
        logger.info("ThirdHandler handle finished.");
        return Optional.ofNullable(result);
    }
}
