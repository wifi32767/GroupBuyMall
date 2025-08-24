package com.wifi32767.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.common.frame.tree.StrategyHandler;
import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.service.trial.AbstractGroupBuyMallSupport;
import com.wifi32767.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description 路由节点 转发请求
 */
@Slf4j
@Service
public class SwitchNode extends AbstractGroupBuyMallSupport<MallProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private MallNode mallNode;

    @Override
    public TrialBalanceEntity doApply(MallProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-SwitchNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        // 根据用户ID切量
        String userId = requestParameter.getUserId();

        // 判断是否降级
        if (repository.downgradeSwitch()) {
            log.info("拼团活动降级拦截 {}", userId);
            throw new AppException(ResponseCode.E003.getCode(), ResponseCode.E003.getInfo());
        }

        // 切量范围判断
        if (!repository.cutRange(userId)) {
            log.info("拼团活动切量拦截 {}", userId);
            throw new AppException(ResponseCode.E004.getCode(), ResponseCode.E004.getInfo());
        }
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MallProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MallProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return mallNode;
    }

}