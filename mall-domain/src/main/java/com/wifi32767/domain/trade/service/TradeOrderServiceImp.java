package com.wifi32767.domain.trade.service;

import com.wifi32767.domain.activity.adapter.repository.TradeRepository;
import com.wifi32767.domain.activity.model.aggregate.GroupBuyOrderAggregate;
import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.activity.model.entity.PayActivityEntity;
import com.wifi32767.domain.activity.model.entity.PayDiscountEntity;
import com.wifi32767.domain.activity.model.entity.UserEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TradeOrderServiceImp implements TradeOrderService {

    @Resource
    private TradeRepository repository;

    @Override
    public MallPayOrderEntity queryNoPayMallPayOrderByOutTradeNo(String userId, String outTradeNo) {
        log.info("拼团交易-查询未支付营销订单:{} outTradeNo:{}", userId, outTradeNo);
        return repository.queryMallPayOrderEntityByOutTradeNo(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        log.info("拼团交易-查询拼单进度:{}", teamId);
        return repository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MallPayOrderEntity lockMallPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        log.info("拼团交易-锁定营销优惠支付订单:{} activityId:{} goodsId:{}", userEntity.getUserId(), payActivityEntity.getActivityId(), payDiscountEntity.getGoodsId());

        // 构建聚合对象
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .build();

        // 锁定聚合订单 - 这会用户只是下单还没有支付。后续会有2个流程；支付成功、超时未支付（回退）
        return repository.lockMallPayOrder(groupBuyOrderAggregate);
    }

}
