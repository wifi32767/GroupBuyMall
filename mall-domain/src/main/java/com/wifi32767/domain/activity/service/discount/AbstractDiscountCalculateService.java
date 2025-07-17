package com.wifi32767.domain.activity.service.discount;

import com.wifi32767.domain.activity.model.valobject.DiscountTypeEnum;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * @description 判断人群是否满足条件，满足条件则进行计算，不满足条件则直接返回原价
 */
public abstract class AbstractDiscountCalculateService implements DiscountCalculateService {
    @Override
    public BigDecimal calculate(String userId, BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount) {
        if (DiscountTypeEnum.TAG.equals(discount.getDiscountType()) && !filterTagId(userId, discount.getTagId())) {
            return original;
        }
        return doCal(original, discount);
    }

    private boolean filterTagId(String userId, String tagId) {
        // todo
        return true;
    }

    protected abstract BigDecimal doCal(BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount);
}
