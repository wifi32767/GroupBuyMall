package com.wifi32767.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class IndexGroupBuyMallServiceTest {

    @Resource
    private IndexGroupBuyMallService indexGroupBuyMallService;

    @Test
    public void test_Trial() throws Exception {
        MallProductEntity mallProductEntity = new MallProductEntity();
        mallProductEntity.setUserId("aaa");
        mallProductEntity.setSource("s1");
        mallProductEntity.setChannel("c1");
        mallProductEntity.setGoodsId("1111");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(mallProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(mallProductEntity));
        log.info("返回结果:{}", JSON.toJSONString(trialBalanceEntity));
    }

    @Test
    public void test_indexMallTrial_no_tag() throws Exception {
        MallProductEntity mallProductEntity = new MallProductEntity();
        mallProductEntity.setUserId("aaa");
        mallProductEntity.setSource("s1");
        mallProductEntity.setChannel("c1");
        mallProductEntity.setGoodsId("1111");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(mallProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(mallProductEntity));
        log.info("返回结果:{}", JSON.toJSONString(trialBalanceEntity));
    }

    @Test
    public void test_indexMallTrial_error() throws Exception {
        MallProductEntity mallProductEntity = new MallProductEntity();
        mallProductEntity.setUserId("aaa");
        mallProductEntity.setSource("s1");
        mallProductEntity.setChannel("c1");
        mallProductEntity.setGoodsId("1112");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(mallProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(mallProductEntity));
        log.info("返回结果:{}", JSON.toJSONString(trialBalanceEntity));
    }
}