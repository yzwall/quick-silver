package com.yuanjie.quicksilver.bo;

import com.yuanjie.quicksilver.enums.EnvEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Builder
@Data
public class ApiCacheKeyGenerateParamBo {

    private EnvEnum env;

    private String apiName;

    private Boolean fromTest;

    private Object[] args;
}
