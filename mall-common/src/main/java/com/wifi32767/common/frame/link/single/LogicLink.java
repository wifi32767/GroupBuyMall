package com.wifi32767.common.frame.link.single;

public interface LogicLink<T, D, R> extends LogicChainArmory<T, D, R> {

    R apply(T requestParameter, D dynamicContext) throws Exception;

}
