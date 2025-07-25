package com.wifi32767.test.domain.trade;

import com.alibaba.fastjson.JSON;
import com.wifi32767.domain.activity.model.entity.*;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import com.wifi32767.domain.trade.service.TradeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class TradeOrderServiceTest {

    @Resource
    private IndexGroupBuyMallService indexGroupBuyMallService;

    @Resource
    private TradeOrderService tradeOrderService;

    @Test
    public void test_lockMallPayOrder() throws Exception {
        // 入参信息
        Long activityId = 100123L;
        String userId = "xiaofuge";
        String goodsId = "9890001";
        String source = "s01";
        String channel = "c01";
        String outTradeNo = "909000098111";

        // 1. 获取试算优惠，有【activityId】优先使用
        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(MallProductEntity.builder()
                .userId(userId)
                .source(source)
                .channel(channel)
                .goodsId(goodsId)
                .activityId(activityId)
                .build());

        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

        // 查询 outTradeNo 是否已经存在交易记录
        MallPayOrderEntity mallPayOrderEntityOld = tradeOrderService.queryNoPayMallPayOrderByOutTradeNo(userId, outTradeNo);
        if (null != mallPayOrderEntityOld) {
            log.info("测试结果(Old):{}", JSON.toJSONString(mallPayOrderEntityOld));
            return;
        }

        // 2. 锁定，营销预支付订单；商品下单前，预购锁定。
        MallPayOrderEntity mallPayOrderEntityNew = tradeOrderService.lockMallPayOrder(
                UserEntity.builder().userId(userId).build(),
                PayActivityEntity.builder()
                        .teamId(null)
                        .activityId(groupBuyActivityDiscountVO.getActivityId())
                        .activityName(groupBuyActivityDiscountVO.getActivityName())
                        .startTime(groupBuyActivityDiscountVO.getStartTime())
                        .endTime(groupBuyActivityDiscountVO.getEndTime())
                        .targetCount(groupBuyActivityDiscountVO.getTarget())
                        .build(),
                PayDiscountEntity.builder()
                        .source(source)
                        .channel(channel)
                        .goodsId(goodsId)
                        .goodsName(trialBalanceEntity.getGoodsName())
                        .originalPrice(trialBalanceEntity.getOriginalPrice())
                        .deductionPrice(trialBalanceEntity.getDeductionPrice())
                        .outTradeNo(outTradeNo)
                        .build());

        log.info("测试结果(New):{}", JSON.toJSONString(mallPayOrderEntityNew));
    }
}