package com.wifi32767.domain.trade.adapter.repository;

import com.wifi32767.domain.activity.model.aggregate.GroupBuyOrderAggregate;
import com.wifi32767.domain.activity.model.entity.GroupBuyActivityEntity;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;
import com.wifi32767.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import com.wifi32767.domain.trade.model.entity.GroupBuyTeamEntity;

public interface TradeRepository {

    MallPayOrderEntity queryMallPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MallPayOrderEntity lockMallPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);

    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);

    void settlementMallPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

}
