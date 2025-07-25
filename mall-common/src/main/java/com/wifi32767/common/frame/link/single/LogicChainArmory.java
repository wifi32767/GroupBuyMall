package com.wifi32767.common.frame.link.single;

public interface LogicChainArmory<T, D, R> {

    LogicLink<T, D, R> next();

    LogicLink<T, D, R> appendNext(LogicLink<T, D, R> next);

}