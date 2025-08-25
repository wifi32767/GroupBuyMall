package com.wifi32767.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("000", "成功"),
    UN_ERROR("001", "未知失败"),
    ILLEGAL_PARAMETER("002", "非法参数"),
    INDEX_EXCEPTION("003", "唯一索引冲突"),
    UPDATE_ZERO("004", "更新记录为0"),
    HTTP_EXCEPTION("005", "HTTP接口调用异常"),

    E001("E001", "不存在对应的折扣计算服务"),
    E002("E002", "无拼团营销配置"),
    E003("E003", "拼团活动降级拦截"),
    E004("E004", "拼团活动切量拦截"),
    E005("E005", "拼团组队失败，记录更新为0"),
    E006("E006", "拼团组队完结，锁单量已达成"),
    E007("E007", "拼团人群限定，不可参与"),
    E008("E008", "拼团组队失败，缓存库存不足"),

    E101("E101", "拼团活动未生效"),
    E102("E102", "不在拼团活动有效时间内"),
    E103("E103", "当前用户参与此拼团次数已达上限"),
    E104("E104", "不存在的外部交易单号或用户已退单"),
    E105("E105", "SC渠道黑名单拦截"),
    E106("E106", "订单交易时间不在拼团有效时间范围内"),

    ;

    private String code;
    private String info;
}
