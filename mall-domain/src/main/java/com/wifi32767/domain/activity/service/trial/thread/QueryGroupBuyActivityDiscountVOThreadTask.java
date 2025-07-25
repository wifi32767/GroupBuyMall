package com.wifi32767.domain.activity.service.trial.thread;

import com.wifi32767.domain.activity.adapter.repository.ActivityRepository;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.SCSkuActivityVO;

import java.util.concurrent.Callable;

/**
 * @description 查询营销配置任务
 */
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {


    /**
     * 活动ID
     */
    private final Long activityId;

    /**
     * 来源
     */
    private final String source;

    /**
     * 渠道
     */
    private final String channel;


    /**
     * 商品ID
     */
    private final String goodsId;


    /**
     * 活动仓储
     */
    private final ActivityRepository activityRepository;

    public QueryGroupBuyActivityDiscountVOThreadTask(Long activityId, String source, String channel, String goodsId, ActivityRepository activityRepository) {
        this.activityId = activityId;
        this.source = source;
        this.channel = channel;
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        // 判断是否存在可用的活动ID
        Long availableActivityId = activityId;
        if (null == activityId) {
            // 查询渠道商品活动配置关联配置
            SCSkuActivityVO scSkuActivityVO = activityRepository.querySCSkuActivityBySCGoodsId(source, channel, goodsId);
            if (null == scSkuActivityVO) return null;
            availableActivityId = scSkuActivityVO.getActivityId();
        }
        // 查询活动配置
        return activityRepository.queryGroupBuyActivityDiscountVO(availableActivityId);
    }

}
