package com.wifi32767.domain.activity.service.trial.node;

import com.wifi32767.common.frame.tree.StrategyHandler;
import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.service.trial.AbstractGroupBuyMallSupport;
import com.wifi32767.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TagNode extends AbstractGroupBuyMallSupport<MallProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private EndNode endNode;

    @Override
    protected TrialBalanceEntity doApply(MallProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        // 获取拼团活动配置
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();

        String tagId = groupBuyActivityDiscountVO.getTagId();
        boolean visible = groupBuyActivityDiscountVO.isVisible();
        boolean enable = groupBuyActivityDiscountVO.isEnable();

        // 人群标签配置为空，则走默认值
        if (StringUtils.isBlank(tagId)) {
            dynamicContext.setVisible(true);
            dynamicContext.setEnable(true);
            return router(requestParameter, dynamicContext);
        }

        // 是否在人群范围内；visible、enable 如果值为 ture 则表示没有配置拼团限制，那么就直接保证为 true 即可
        boolean isWithin = repository.isTagCrowdRange(tagId, requestParameter.getUserId());
        dynamicContext.setVisible(visible || isWithin);
        dynamicContext.setEnable(enable || isWithin);

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MallProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MallProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }

}