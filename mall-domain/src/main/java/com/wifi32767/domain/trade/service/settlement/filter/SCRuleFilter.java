package com.wifi32767.domain.trade.service.settlement.filter;

import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.common.frame.link.multi.handler.LogicHandler;
import com.wifi32767.domain.trade.adapter.repository.TradeRepository;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.wifi32767.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description SC 渠道来源过滤 - 当某个签约渠道下架后，则不会记账
 */
@Slf4j
@Service
public class SCRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-渠道黑名单校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // sc 渠道黑名单拦截
        boolean intercept = repository.isSCBlackIntercept(requestParameter.getSource(), requestParameter.getChannel());
        if (intercept) {
            log.error("{}{} 渠道黑名单拦截", requestParameter.getSource(), requestParameter.getChannel());
            throw new AppException(ResponseCode.E105);
        }

        return next(requestParameter, dynamicContext);
    }

}
