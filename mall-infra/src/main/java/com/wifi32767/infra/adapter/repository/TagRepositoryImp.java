package com.wifi32767.infra.adapter.repository;

import com.wifi32767.domain.tag.adapter.TagRepository;
import com.wifi32767.domain.tag.model.entity.CrowdTagsJobEntity;
import com.wifi32767.infra.dao.CrowdTagsDao;
import com.wifi32767.infra.dao.CrowdTagsDetailDao;
import com.wifi32767.infra.dao.CrowdTagsJobDao;
import com.wifi32767.infra.dao.po.CrowdTags;
import com.wifi32767.infra.dao.po.CrowdTagsDetail;
import com.wifi32767.infra.dao.po.CrowdTagsJob;
import com.wifi32767.infra.redis.RedisService;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TagRepositoryImp implements TagRepository {

    @Resource
    private CrowdTagsDao crowdTagsDao;
    @Resource
    private CrowdTagsDetailDao crowdTagsDetailDao;
    @Resource
    private CrowdTagsJobDao crowdTagsJobDao;

    @Resource
    private RedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);

        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        if (null == crowdTagsJobRes) return null;

        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);

        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);

            // 获取BitSet
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);
        } catch (DuplicateKeyException ignore) {
            // 忽略唯一索引冲突
        }
    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int count) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(count);

        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
