package com.wifi32767.infra.dao;

import com.wifi32767.infra.dao.po.CrowdTags;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrowdTagsDao {

    void updateCrowdTagsStatistics(CrowdTags crowdTagsReq);

}
