package com.wifi32767.domain.activity.adapter.repository;

import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.SCSkuActivityVO;
import com.wifi32767.domain.activity.model.valobject.SkuVO;

/**
 * @description 活动仓储接口
 */
public interface ActivityRepository {
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    boolean isTagCrowdRange(String tagId, String userId);

    boolean downgradeSwitch();

    boolean cutRange(String userId);

}
