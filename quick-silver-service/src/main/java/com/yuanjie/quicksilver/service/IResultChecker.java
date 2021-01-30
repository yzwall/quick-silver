package com.yuanjie.quicksilver.service;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
public interface IResultChecker {

    <T> boolean check(T result);
}
