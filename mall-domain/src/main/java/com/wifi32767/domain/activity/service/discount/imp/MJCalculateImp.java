package com.wifi32767.domain.activity.service.discount.imp;

import com.wifi32767.common.Constants;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description 满减
 */
@Slf4j
@Service("MJ")
public class MJCalculateImp extends AbstractDiscountCalculateService {

    @Override
    public BigDecimal doCal(BigDecimal original, GroupBuyActivityDiscountVO.GroupBuyDiscount discount) {
        log.info("满减计算:{}", discount.getDiscountType().getCode());

        // 折扣表达式 - 100,10 满100减10元
        String mallExpr = discount.getMallExpr();
        String[] split = mallExpr.split(Constants.SPLIT);
        BigDecimal x = new BigDecimal(split[0].trim());
        BigDecimal y = new BigDecimal(split[1].trim());

        // 不满足最低满减约束，则按照原价
        if (original.compareTo(x) < 0) {
            return original;
        }

        // 折扣价格
        BigDecimal deductionPrice = original.subtract(y);

        // 判断折扣后金额，最低支付1分钱
        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return deductionPrice;
    }

}
