package com.wifi32767.domain.activity.service.trial;

import com.wifi32767.common.frame.tree.AbstractMultiThreadStrategyRouter;
import com.wifi32767.domain.activity.adapter.repository.ActivityRepository;
import com.wifi32767.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractGroupBuyMallSupport<MallProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractMultiThreadStrategyRouter<com.wifi32767.domain.activity.model.entity.MallProductEntity, DefaultActivityStrategyFactory.DynamicContext, com.wifi32767.domain.activity.model.entity.TrialBalanceEntity> {

    protected long timeout = 500;
    @Resource
    protected ActivityRepository repository;

    @Override
    protected void multiThread(com.wifi32767.domain.activity.model.entity.MallProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的方法
    }

}
