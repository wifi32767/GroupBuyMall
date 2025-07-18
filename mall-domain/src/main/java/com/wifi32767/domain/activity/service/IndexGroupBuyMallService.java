package com.wifi32767.domain.activity.service;


import com.wifi32767.domain.activity.model.entity.MallProductEntity;
import com.wifi32767.domain.activity.model.entity.TrialBalanceEntity;

public interface IndexGroupBuyMallService {
    /**
     * @description 根据商品信息试算拼团所需花费
     */
    TrialBalanceEntity indexMallTrial(MallProductEntity mallProductEntity) throws Exception;

}
