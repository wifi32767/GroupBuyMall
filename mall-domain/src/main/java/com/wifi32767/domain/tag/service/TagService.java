package com.wifi32767.domain.tag.service;

public interface TagService {
    /**
     * 执行人群标签批次任务
     *
     * @param tagId
     * @param batchId
     */
    void execTagBatchJob(String tagId, String batchId);

}
