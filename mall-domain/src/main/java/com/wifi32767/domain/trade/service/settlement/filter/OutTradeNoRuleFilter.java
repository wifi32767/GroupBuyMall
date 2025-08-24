package com.wifi32767.domain.trade.service.settlement.filter;


import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.common.frame.link.multi.handler.LogicHandler;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.trade.adapter.repository.TradeRepository;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.wifi32767.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description 外部交易单号过滤；外部交易单号是否为退单
 */
@Slf4j
@Service
public class OutTradeNoRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-外部单号校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // 查询拼团信息
        MallPayOrderEntity mallPayOrderEntity = repository.queryMallPayOrderEntityByOutTradeNo(requestParameter.getUserId(), requestParameter.getOutTradeNo());

        if (null == mallPayOrderEntity) {
            log.error("不存在的外部交易单号或用户已退单，不需要做支付订单结算:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E104);
        }

        dynamicContext.setMallPayOrderEntity(mallPayOrderEntity);

        return next(requestParameter, dynamicContext);
    }

}
