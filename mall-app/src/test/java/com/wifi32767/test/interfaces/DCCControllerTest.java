package com.wifi32767.test.interfaces;

import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.interfaces.http.DCCController;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class DCCControllerTest {

    @Resource
    private DCCController dccController;

    @Resource
    private IndexGroupBuyMallService indexGroupBuyMarketService;

    @Test
    public void test_updateConfig() {
        // 动态调整配置
        dccController.updateConfig("downgradeSwitch", "1");
    }

    @Test
    public void test_updateConfig2indexMarketTrial() throws Exception {
        // 动态调整配置
        dccController.updateConfig("downgradeSwitch", "1");
        // 超时等待异步
        Thread.sleep(1000);

        // 营销验证
        MallProductEntity marketProductEntity = new MallProductEntity();
        marketProductEntity.setUserId("xiaofuge");
        marketProductEntity.setSource("s01");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setGoodsId("9890001");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMallTrial(marketProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(marketProductEntity));
        log.info("返回结果:{}", JSON.toJSONString(trialBalanceEntity));
    }


}
