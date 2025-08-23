package com.wifi32767.domain.activity.model.aggregate;

import com.wifi32767.domain.activity.model.entity.PayActivityEntity;
import com.wifi32767.domain.activity.model.entity.PayDiscountEntity;
import com.wifi32767.domain.activity.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 拼团订单聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    /**
     * 用户实体对象
     */
    private UserEntity userEntity;
    /**
     * 支付活动实体对象
     */
    private PayActivityEntity payActivityEntity;
    /**
     * 支付优惠实体对象
     */
    private PayDiscountEntity payDiscountEntity;
    /**
     * 已参与拼团量
     */
    private Integer userTakeOrderCount;

}
