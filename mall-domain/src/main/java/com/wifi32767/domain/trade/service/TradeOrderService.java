package com.wifi32767.domain.trade.service;

import com.wifi32767.domain.activity.model.entity.MallPayOrderEntity;
import com.wifi32767.domain.activity.model.entity.PayActivityEntity;
import com.wifi32767.domain.activity.model.entity.PayDiscountEntity;
import com.wifi32767.domain.activity.model.entity.UserEntity;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;

/**
 * @description 交易订单服务接口
 */
public interface TradeOrderService {

    /**
     * 查询，未被支付消费完成的营销优惠订单
     *
     * @param userId     用户ID
     * @param outTradeNo 外部唯一单号
     * @return 拼团，预购订单营销实体对象
     */
    MallPayOrderEntity queryNoPayMallPayOrderByOutTradeNo(String userId, String outTradeNo);

    /**
     * 查询拼团进度
     *
     * @param teamId 拼团ID
     * @return 进度
     */
    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    /**
     * 锁定，营销预支付订单；商品下单前，预购锁定。
     *
     * @param userEntity        用户根实体对象
     * @param payActivityEntity 拼团，支付活动实体对象
     * @param payDiscountEntity 拼团，支付优惠实体对象
     * @return 拼团，预购订单营销实体对象
     */
    MallPayOrderEntity lockMallPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) throws Exception;

}
