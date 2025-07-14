package com.wifi32767.infra.dao;

import com.wifi32767.infra.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuDao {

    Sku querySkuByGoodsId(String goodsId);

}
