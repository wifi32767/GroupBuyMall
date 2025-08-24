package com.wifi32767.domain.activity.service;


import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import com.wifi32767.domain.activity.model.valobject.TeamStatisticVO;

import java.util.List;

public interface IndexGroupBuyMallService {
    /**
     * @description 根据商品信息试算拼团所需花费
     */
    TrialBalanceEntity indexMallTrial(MallProductEntity mallProductEntity) throws Exception;

    /**
     * 查询进行中的拼团订单
     *
     * @param activityId  活动ID
     * @param userId      用户ID
     * @param ownerCount  个人数量
     * @param randomCount 随机数量
     * @return 用户拼团明细数据
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount);

    /**
     * 活动拼团队伍总结
     *
     * @param activityId 活动ID
     * @return 队伍统计
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);

}
