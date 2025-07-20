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

    E001("E001", "不存在对应的折扣计算服务"),
    E002("E002", "无拼团营销配置"),
    E0003("E0003", "拼团活动降级拦截"),
    E0004("E0004", "拼团活动切量拦截"),
    ;

    private String code;
    private String info;
}
