package com.wifi32767.test.interfaces;

import com.alibaba.fastjson.JSON;
import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import com.wifi32767.interfaces.http.DCCController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class DCCControllerTest {

    @Resource
    private DCCController dccController;

    @Resource
    private IndexGroupBuyMallService indexGroupBuyMallService;

    @Test
    public void test_updateConfig() {
        // 动态调整配置
        dccController.updateConfig("downgradeSwitch", "1");
    }

    @Test
    public void test_updateConfig2indexMallTrial() throws Exception {
        // 动态调整配置
        dccController.updateConfig("downgradeSwitch", "1");
        // 超时等待异步
        Thread.sleep(1000);

        // 营销验证
        MallProductEntity mallProductEntity = new MallProductEntity();
        mallProductEntity.setUserId("a");
        mallProductEntity.setSource("s1");
        mallProductEntity.setChannel("c1");
        mallProductEntity.setGoodsId("1111");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(mallProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(mallProductEntity));
        log.info("返回结果:{}", JSON.toJSONString(trialBalanceEntity));
    }


}
