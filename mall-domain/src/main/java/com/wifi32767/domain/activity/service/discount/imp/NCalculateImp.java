package com.wifi32767.domain.activity.service.discount.imp;

import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description N元购
 */
@Slf4j
@Service("N")
public class NCalculateImp extends AbstractDiscountCalculateService {

    @Override
    public BigDecimal doCal(BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount) {
        log.info("N元购计算:{}", discount.getDiscountType().getCode());

        // 直接为优惠后的金额
        return new BigDecimal(discount.getMallExpr());
    }

}