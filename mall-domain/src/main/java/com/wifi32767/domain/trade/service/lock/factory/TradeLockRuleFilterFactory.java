package com.wifi32767.domain.trade.service.lock.factory;

import com.wifi32767.common.frame.link.multi.chain.BusinessLinkedList;
import com.wifi32767.common.frame.link.multi.chain.LinkArmory;
import com.wifi32767.domain.activity.model.entity.GroupBuyActivityEntity;
import com.wifi32767.domain.activity.model.entity.TradeLockRuleCommandEntity;
import com.wifi32767.domain.activity.model.entity.TradeLockRuleFilterBackEntity;
import com.wifi32767.domain.trade.service.lock.filter.ActivityUsabilityRuleFilter;
import com.wifi32767.domain.trade.service.lock.filter.UserTakeLimitRuleFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @description 交易规则过滤工厂
 */
@Slf4j
@Service
public class TradeLockRuleFilterFactory {

    @Bean("tradeRuleFilter")
    public BusinessLinkedList<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        // 组装链
        LinkArmory<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易规则过滤链", activityUsabilityRuleFilter, userTakeLimitRuleFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private GroupBuyActivityEntity groupBuyActivity;

    }

}
