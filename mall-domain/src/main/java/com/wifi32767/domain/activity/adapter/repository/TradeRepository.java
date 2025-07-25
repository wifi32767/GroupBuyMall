package com.wifi32767.domain.activity.adapter.repository;

import com.wifi32767.domain.activity.model.aggregate.GroupBuyOrderAggregate;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;

public interface TradeRepository {

    MallPayOrderEntity queryMallPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MallPayOrderEntity lockMallPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

}
