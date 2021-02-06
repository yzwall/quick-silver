package com.yuanjie.quicksilver.pattern.responsibility.handler;

import com.yuanjie.quicksilver.pattern.responsibility.annotation.HandlerTag;
import com.yuanjie.quicksilver.pattern.responsibility.enums.HandlerEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@HandlerTag(HandlerEnum.HANDLER_NO_2)
@Component
public class SecondHandler extends AbstractHandler<Object, String> {

    @Override
    public Optional<String> handle(Object context) {
        String result = "SecondHandler handle finished.";
        logger.info("SecondHandler handle finished.");
        return Optional.ofNullable(result);
    }
}
