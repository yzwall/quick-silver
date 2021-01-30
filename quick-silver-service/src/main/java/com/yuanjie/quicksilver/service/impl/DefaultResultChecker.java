package com.yuanjie.quicksilver.service.impl;

import com.yuanjie.quicksilver.service.IResultChecker;
import org.springframework.stereotype.Component;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Component
public class DefaultResultChecker implements IResultChecker {

    @Override
    public <T> boolean check(T result) {
        return true;
    }
}
