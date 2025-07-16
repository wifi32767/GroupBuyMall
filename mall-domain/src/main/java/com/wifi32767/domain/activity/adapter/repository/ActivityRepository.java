package com.wifi32767.domain.activity.adapter.repository;

import com.wifi32767.common.frame.tree.StrategyHandler;
import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.SkuVO;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import com.wifi32767.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;

import javax.annotation.Resource;

/**
 * @description 活动仓储接口
 */
public interface ActivityRepository {
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);
}
