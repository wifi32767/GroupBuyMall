package com.wifi32767.test.infra.dao;

import com.wifi32767.infra.dao.GroupBuyActivityDao;
import com.wifi32767.infra.dao.po.GroupBuyActivity;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class GroupBuyActivityDaoTest {

    @Autowired
    private GroupBuyActivityDao groupBuyActivityDao;

    @Test
    public void test_queryGroupBuyActivityList() {
        log.info("测试开始");
        List<GroupBuyActivity> groupBuyActivities = groupBuyActivityDao.queryGroupBuyActivityList();
        log.info("测试结果:{}", JSON.toJSONString(groupBuyActivities));
    }

//    private static void printVersions() {
//        System.out.println("========================================");
//        System.out.println("Spring Boot Version: " + SpringBootVersion.getVersion());
//        System.out.println("Spring Framework Version: " + SpringVersion.getVersion());
//        System.out.println("Java Version: " + System.getProperty("java.version"));
//        System.out.println("========================================");
//    }

}
