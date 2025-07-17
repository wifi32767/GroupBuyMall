package com.wifi32767.domain.activity.adapter.repository;

import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.SkuVO;

/**
 * @description 活动仓储接口
 */
public interface ActivityRepository {
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);
}
