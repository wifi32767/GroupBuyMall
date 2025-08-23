package com.wifi32767.domain.activity.service.discount;

import com.wifi32767.domain.activity.adapter.repository.ActivityRepository;
import com.wifi32767.domain.activity.model.valobject.DiscountTypeEnum;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @description 判断人群是否满足条件，满足条件则进行计算，不满足条件则直接返回原价
 */

@Slf4j
public abstract class AbstractDiscountCalculateService implements DiscountCalculateService {

    @Resource
    protected ActivityRepository repository;

    @Override
    public BigDecimal calculate(String userId, BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount) {
        if (DiscountTypeEnum.TAG.equals(discount.getDiscountType()) && !filterTagId(userId, discount.getTagId())) {
            log.info("折扣优惠计算拦截，用户不再优惠人群标签范围内 userId:{}", userId);
            return original;
        }
        return doCal(original, discount);
    }

    private boolean filterTagId(String userId, String tagId) {
        return repository.isTagCrowdRange(tagId, userId);
    }

    protected abstract BigDecimal doCal(BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount);
}
