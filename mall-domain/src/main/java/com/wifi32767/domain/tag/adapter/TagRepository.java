package com.wifi32767.domain.tag.adapter;

import com.wifi32767.domain.tag.model.entity.CrowdTagsJobEntity;

/**
 * @description 人群标签仓储
 */
public interface TagRepository {
    CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId);

    void addCrowdTagsUserId(String tagId, String userId);

    void updateCrowdTagsStatistics(String tagId, int count);

}
