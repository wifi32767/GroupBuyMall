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
    ;

    private String code;
    private String info;
}
