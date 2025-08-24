package com.wifi32767.domain.trade.service.settlement.filter;


import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.common.frame.link.multi.handler.LogicHandler;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.trade.adapter.repository.TradeRepository;
import com.wifi32767.domain.trade.model.entity.GroupBuyTeamEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.wifi32767.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description 可结算规则过滤；交易时间
 */
@Slf4j
@Service
public class SettableRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-有效时间校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // 上下文；获取数据
        MallPayOrderEntity mallPayOrderEntity = dynamicContext.getMallPayOrderEntity();

        // 查询拼团对象
        GroupBuyTeamEntity groupBuyTeamEntity = repository.queryGroupBuyTeamByTeamId(mallPayOrderEntity.getTeamId());

        // 外部交易时间 - 也就是用户支付完成的时间，这个时间要在拼团有效时间范围内
        Date outTradeTime = requestParameter.getOutTradeTime();

        // 判断，外部交易时间，要小于拼团结束时间。否则抛异常。
        if (!outTradeTime.before(groupBuyTeamEntity.getValidEndTime())) {
            log.error("订单交易时间不在拼团有效时间范围内");
            throw new AppException(ResponseCode.E106);
        }

        // 设置上下文
        dynamicContext.setGroupBuyTeamEntity(groupBuyTeamEntity);

        return next(requestParameter, dynamicContext);
    }

}
