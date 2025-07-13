package com.wifi32767.common.frame.tree;

/**
 *
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 返回结果类型
 */
public interface StrategyMapper<T, D, R> {
    /**
     * @description 获取策略处理器
     *
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;

}