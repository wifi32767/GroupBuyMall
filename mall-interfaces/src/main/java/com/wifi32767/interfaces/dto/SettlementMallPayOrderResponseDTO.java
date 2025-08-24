package com.wifi32767.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 结算应答对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementMallPayOrderResponseDTO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 外部交易单号
     */
    private String outTradeNo;

}
