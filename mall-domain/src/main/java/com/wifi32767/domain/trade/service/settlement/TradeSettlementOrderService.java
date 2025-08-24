package com.wifi32767.domain.trade.service.settlement;

import com.wifi32767.domain.trade.model.entity.TradePaySettlementEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySuccessEntity;

import java.util.Map;

/**
 * @description 拼团交易结算服务接口
 */
public interface TradeSettlementOrderService {
    /**
     * 营销结算
     *
     * @param tradePaySuccessEntity 交易支付订单实体对象
     * @return 交易结算订单实体
     */
    TradePaySettlementEntity settlementMallPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception;

    /**
     * 执行结算通知任务
     *
     * @return 结算数量
     * @throws Exception 异常
     */
    Map<String, Integer> execSettlementNotifyJob() throws Exception;

    /**
     * 执行结算通知任务
     *
     * @param teamId 指定结算组ID
     * @return 结算数量
     * @throws Exception 异常
     */
    Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception;

}
