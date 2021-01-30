package com.yuanjie.quicksilver.service;

import com.yuanjie.quicksilver.bo.ApiCacheKeyGenerateParamBo;

import java.util.Optional;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
public interface IKeyGenerator {

    Optional<String> generateKey(ApiCacheKeyGenerateParamBo param);
}
