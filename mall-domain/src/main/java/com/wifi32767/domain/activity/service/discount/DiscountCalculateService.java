package com.wifi32767.domain.activity.service.discount;

import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * @description 折扣计算
 */
public interface DiscountCalculateService {
    BigDecimal calculate(String userId, BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount);
}
