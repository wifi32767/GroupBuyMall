package com.wifi32767.domain.trade.service.lock.filter;

import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.common.frame.link.multi.handler.LogicHandler;
import com.wifi32767.domain.activity.model.entity.GroupBuyActivityEntity;
import com.wifi32767.domain.activity.model.entity.TradeLockRuleCommandEntity;
import com.wifi32767.domain.activity.model.entity.TradeLockRuleFilterBackEntity;
import com.wifi32767.domain.trade.adapter.repository.TradeRepository;
import com.wifi32767.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description 用户参与限制，规则过滤
 */
@Slf4j
@Service
public class UserTakeLimitRuleFilter implements LogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private TradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        GroupBuyActivityEntity groupBuyActivity = dynamicContext.getGroupBuyActivity();

        // 查询用户在一个拼团活动上参与的次数
        Integer count = repository.queryOrderCountByActivityId(requestParameter.getActivityId(), requestParameter.getUserId());

        if (null != groupBuyActivity.getTakeLimitCount() && count >= groupBuyActivity.getTakeLimitCount()) {
            log.info("用户参与次数校验，已达可参与上限 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E103);
        }

        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(count)
                .build();
    }

}
