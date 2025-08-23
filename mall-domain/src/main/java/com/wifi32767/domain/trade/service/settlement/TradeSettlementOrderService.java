package com.wifi32767.domain.trade.service.settlement;

import com.wifi32767.domain.trade.model.entity.TradePaySettlementEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySuccessEntity;

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
    TradePaySettlementEntity settlementMallPayOrder(TradePaySuccessEntity tradePaySuccessEntity);

}
