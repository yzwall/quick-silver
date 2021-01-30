package com.yuanjie.quicksilver.aop;

import com.yuanjie.quicksilver.annotation.EnableApiCache;
import com.yuanjie.quicksilver.bo.ApiCacheKeyGenerateParamBo;
import com.yuanjie.quicksilver.enums.EnvEnum;
import com.yuanjie.quicksilver.manager.RedisManager;
import com.yuanjie.quicksilver.service.IKeyGenerator;
import com.yuanjie.quicksilver.service.IResultChecker;
import com.yuanjie.quicksilver.switchcenter.DynamicSwitch;
import com.yuanjie.quicksilver.util.ApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Slf4j
@Aspect
@Component
public class EnableApiCacheInterceptor {

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private RedisManager redisManager;

    @Pointcut("@annotation(com.yuanjie.quicksilver.annotation.EnableApiCache)")
    public void pointCount() {
    }

    private boolean blockedByApiCacheBlackKey(String apiName) {
        return DynamicSwitch.enbaleApiCacheBlackKey
            && CollectionUtils.isNotEmpty(DynamicSwitch.apiCacheBlackKeyList)
            && DynamicSwitch.apiCacheBlackKeyList.contains(apiName);
    }

    private String generateCacheKey(String apiName,  Class<? extends IKeyGenerator> clazz, Object[] args) {
        return applicationUtil.getBeanByType(clazz).flatMap(keyGenerator -> {
            ApiCacheKeyGenerateParamBo param = ApiCacheKeyGenerateParamBo.builder()
                .apiName(apiName)
                .env(EnvEnum.PUBLISH)
                .fromTest(false)
                .args(args)
                .build();
            return keyGenerator.generateKey(param);
        }).orElse(null);
    }

    private boolean checkResult(Object result, Class<? extends IResultChecker> clazz) {
        return applicationUtil.getBeanByType(clazz).map(checker -> checker.check(result)).orElse(false);
    }

    @Around("pointCount()")
    public Object apiCacheAdvice(ProceedingJoinPoint point) {
        try {
            if (!DynamicSwitch.enableApiCache) {
                return point.proceed();
            }
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            Method method = methodSignature.getMethod();
            EnableApiCache enableApiCache = method.getAnnotation(EnableApiCache.class);
            if (Objects.isNull(enableApiCache)) {
                return point.proceed();
            }
            String apiName = enableApiCache.apiName();
            if (blockedByApiCacheBlackKey(apiName)) {
                return point.proceed();
            }

            // fetch data from cache
            String cacheKey = generateCacheKey(apiName, enableApiCache.keyGenerator(), point.getArgs());
            if (StringUtils.isBlank(cacheKey)) {
                return point.proceed();
            }
            Class<?> returnType = method.getReturnType();

            Optional cacheResult = redisManager.get(cacheKey, returnType);
            if (cacheResult.isPresent()) {
                return cacheResult.get();
            }

            Object result = point.proceed();
            boolean checkResult = checkResult(result, enableApiCache.resultChecker());
            if (checkResult) {
                redisManager.setex(cacheKey, (Serializable)result, DateTimeConstants.SECONDS_PER_DAY);
            }
            return result;
        } catch (Throwable e) {
            log.error("apiCacheAdvice error occurs.", e);
        }
        return null;
    }


}
