package com.wifi32767.domain.trade.service.settlement.factory;

import com.wifi32767.common.frame.link.multi.chain.BusinessLinkedList;
import com.wifi32767.common.frame.link.multi.chain.LinkArmory;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.trade.model.entity.GroupBuyTeamEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import com.wifi32767.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.wifi32767.domain.trade.service.settlement.filter.EndRuleFilter;
import com.wifi32767.domain.trade.service.settlement.filter.OutTradeNoRuleFilter;
import com.wifi32767.domain.trade.service.settlement.filter.SCRuleFilter;
import com.wifi32767.domain.trade.service.settlement.filter.SettableRuleFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @description 交易结算规则过滤工厂
 */
@Slf4j
@Service
public class TradeSettlementRuleFilterFactory {

    @Bean("tradeSettlementRuleFilter")
    public BusinessLinkedList<TradeSettlementRuleCommandEntity,
            DynamicContext, TradeSettlementRuleFilterBackEntity> tradeSettlementRuleFilter(
            SCRuleFilter scRuleFilter,
            OutTradeNoRuleFilter outTradeNoRuleFilter,
            SettableRuleFilter settableRuleFilter,
            EndRuleFilter endRuleFilter) {

        // 组装链
        LinkArmory<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易结算规则过滤链", scRuleFilter, outTradeNoRuleFilter, settableRuleFilter, endRuleFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        // 订单营销实体对象
        private MallPayOrderEntity mallPayOrderEntity;
        // 拼团组队实体对象
        private GroupBuyTeamEntity groupBuyTeamEntity;
    }

}

