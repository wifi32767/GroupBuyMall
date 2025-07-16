package com.wifi32767.common.frame.tree;

/**
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 返回结果类型
 */
public interface StrategyHandler<T, D, R> {
    StrategyHandler DEFAULT = (T, D) -> null;

    /**
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     * @description 处理策略
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;
}