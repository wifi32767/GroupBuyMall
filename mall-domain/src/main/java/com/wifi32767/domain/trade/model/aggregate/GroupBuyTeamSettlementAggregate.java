package com.wifi32767.domain.trade.model.aggregate;

import com.wifi32767.domain.activity.model.entity.UserEntity;
import com.wifi32767.domain.trade.model.entity.GroupBuyTeamEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySuccessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 拼团组队结算聚合
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamSettlementAggregate {

    /**
     * 用户实体对象
     */
    private UserEntity userEntity;
    /**
     * 拼团组队实体对象
     */
    private GroupBuyTeamEntity groupBuyTeamEntity;
    /**
     * 交易支付订单实体对象
     */
    private TradePaySuccessEntity tradePaySuccessEntity;

}