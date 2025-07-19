package com.wifi32767.domain.activity.service.trial.thread;

import com.wifi32767.domain.activity.adapter.repository.ActivityRepository;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.SCSkuActivityVO;

import java.util.concurrent.Callable;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 查询营销配置任务
 * @create 2024-12-21 09:46
 */
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

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

    public QueryGroupBuyActivityDiscountVOThreadTask(String source, String channel, String goodsId, ActivityRepository activityRepository) {
        this.source = source;
        this.channel = channel;
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        SCSkuActivityVO scSkuActivityVO = activityRepository.querySCSkuActivityBySCGoodsId(source, channel, goodsId);
        if (null == scSkuActivityVO) {
            return null;
        }

        return activityRepository.queryGroupBuyActivityDiscountVO(scSkuActivityVO.getActivityId());
    }

}
