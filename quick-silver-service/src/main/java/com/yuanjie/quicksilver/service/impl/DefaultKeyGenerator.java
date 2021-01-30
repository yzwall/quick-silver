package com.yuanjie.quicksilver.service.impl;

import com.yuanjie.quicksilver.bo.ApiCacheKeyGenerateParamBo;
import com.yuanjie.quicksilver.service.IKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Component
public class DefaultKeyGenerator implements IKeyGenerator {

    @Override
    public Optional<String> generateKey(ApiCacheKeyGenerateParamBo param) {
        return Optional.empty();
    }
}
