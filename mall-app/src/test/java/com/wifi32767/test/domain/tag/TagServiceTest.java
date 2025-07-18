package com.wifi32767.test.domain.tag;

import com.wifi32767.domain.tag.service.TagService;
import com.wifi32767.infra.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class TagServiceTest {

    @Resource
    private TagService tagService;
    @Resource
    private RedisService redisService;

    @Test
    public void test_tag_job() {
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV", "10001");
    }

    @Test
    public void test_get_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        // 是否存在
        log.info("a 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("a")));
        log.info("c 不存在，预期结果为 false，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("c")));
    }

}
