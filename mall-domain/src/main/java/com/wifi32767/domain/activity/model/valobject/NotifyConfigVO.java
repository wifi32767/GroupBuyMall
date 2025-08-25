package com.wifi32767.domain.activity.model.valobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description 回调配置值对象
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyConfigVO {

    /**
     * 回调方式；MQ、HTTP
     */
    private NotifyTypeEnumVO notifyType;
    /**
     * 回调消息
     */
    private String notifyTag;
    /**
     * 回调地址
     */
    private String notifyUrl;

}
