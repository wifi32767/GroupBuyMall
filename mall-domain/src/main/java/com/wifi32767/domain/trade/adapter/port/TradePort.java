package com.wifi32767.domain.trade.adapter.port;

import com.wifi32767.domain.trade.model.entity.NotifyTaskEntity;

/**
 * @description 交易接口服务接口
 */
public interface TradePort {
    String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception;

}
