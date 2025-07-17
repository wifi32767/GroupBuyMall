package com.wifi32767.domain.activity.service.discount.imp;

import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description 直减
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateImp extends AbstractDiscountCalculateService {

    @Override
    public BigDecimal doCal(BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount) {
        log.info("直减计算:{}", discount.getDiscountType().getCode());

        // 折扣表达式 - 直减为扣减金额
        String mallExpr = discount.getMallExpr();

        // 折扣价格
        BigDecimal deductionPrice = original.subtract(new BigDecimal(mallExpr));

        // 判断折扣后金额，最低支付1分钱
        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return deductionPrice;
    }
}
