package com.wifi32767.test.domain.trade;


import com.alibaba.fastjson.JSON;
import com.wifi32767.domain.trade.model.entity.TradePaySettlementEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySuccessEntity;
import com.wifi32767.domain.trade.service.settlement.TradeSettlementOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)

public class TradeSettlementOrderServiceTest {

    @Resource
    private TradeSettlementOrderService tradeSettlementOrderService;

    @Test
    public void test_settlementMallPayOrder() throws Exception {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("xfg04");
        tradePaySuccessEntity.setOutTradeNo("228984300880");
        tradePaySuccessEntity.setOutTradeTime(new Date());
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMallPayOrder(tradePaySuccessEntity);
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));
    }

}