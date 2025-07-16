package com.wifi32767.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 商品实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MallProductEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 渠道
     */
    private String source;
    /**
     * 来源
     */
    private String channel;

}
