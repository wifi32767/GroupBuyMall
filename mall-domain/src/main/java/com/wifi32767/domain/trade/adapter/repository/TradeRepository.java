package com.wifi32767.domain.trade.adapter.repository;

import com.wifi32767.domain.activity.model.aggregate.GroupBuyOrderAggregate;
import com.wifi32767.domain.activity.model.entity.GroupBuyActivityEntity;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;
import com.wifi32767.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import com.wifi32767.domain.trade.model.entity.GroupBuyTeamEntity;
import com.wifi32767.domain.trade.model.entity.NotifyTaskEntity;

import java.util.List;

public interface TradeRepository {

    MallPayOrderEntity queryMallPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MallPayOrderEntity lockMallPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);

    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);

    boolean settlementMallPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

    boolean isSCBlackIntercept(String source, String channel);

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList();

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList(String teamId);

    int updateNotifyTaskStatusSuccess(String teamId);

    int updateNotifyTaskStatusError(String teamId);

    int updateNotifyTaskStatusRetry(String teamId);

}
