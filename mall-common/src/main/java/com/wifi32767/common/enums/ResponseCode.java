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


    E001("E001", "不存在对应的折扣计算服务"),
    E002("E002", "无拼团营销配置"),
    E0003("E003", "拼团活动降级拦截"),
    E0004("E004", "拼团活动切量拦截"),
    E0005("E005", "拼团组队失败，记录更新为0"),
    E0006("E006", "拼团组队完结，锁单量已达成"),
    E0007("E007", "拼团人群限定，不可参与"),

    E0101("E101", "拼团活动未生效"),
    E0102("E102", "不在拼团活动有效时间内"),
    E0103("E103", "当前用户参与此拼团次数已达上限"),

    ;

    private String code;
    private String info;
}
