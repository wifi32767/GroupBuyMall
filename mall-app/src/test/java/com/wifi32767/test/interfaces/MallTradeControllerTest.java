package com.wifi32767.test.interfaces;

import com.alibaba.fastjson.JSON;
import com.wifi32767.interfaces.dto.LockMallPayOrderRequestDTO;
import com.wifi32767.interfaces.dto.LockMallPayOrderResponseDTO;
import com.wifi32767.interfaces.http.MallTradeController;
import com.wifi32767.interfaces.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class MallTradeControllerTest {

    @Resource
    private MallTradeController mallTradeController;

    @Test
    public void test_lockMallPayOrder() {
        LockMallPayOrderRequestDTO lockMallPayOrderRequestDTO = new LockMallPayOrderRequestDTO();
        lockMallPayOrderRequestDTO.setUserId("xiaofuge");
        lockMallPayOrderRequestDTO.setTeamId(null);
        lockMallPayOrderRequestDTO.setActivityId(100123L);
        lockMallPayOrderRequestDTO.setGoodsId("9890001");
        lockMallPayOrderRequestDTO.setSource("s01");
        lockMallPayOrderRequestDTO.setChannel("c01");
        lockMallPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));

        Response<LockMallPayOrderResponseDTO> lockMallPayOrderResponseDTOResponse = mallTradeController.lockMallPayOrder(lockMallPayOrderRequestDTO);

        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMallPayOrderRequestDTO), JSON.toJSONString(lockMallPayOrderResponseDTOResponse));
    }

    @Test
    public void test_lockMallPayOrder_teamId_not_null() {
        LockMallPayOrderRequestDTO lockMallPayOrderRequestDTO = new LockMallPayOrderRequestDTO();
        lockMallPayOrderRequestDTO.setUserId("xiaofuge");
        lockMallPayOrderRequestDTO.setTeamId("33063446");
        lockMallPayOrderRequestDTO.setActivityId(100123L);
        lockMallPayOrderRequestDTO.setGoodsId("9890001");
        lockMallPayOrderRequestDTO.setSource("s01");
        lockMallPayOrderRequestDTO.setChannel("c01");
        lockMallPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));

        Response<LockMallPayOrderResponseDTO> lockMallPayOrderResponseDTOResponse = mallTradeController.lockMallPayOrder(lockMallPayOrderRequestDTO);

        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMallPayOrderRequestDTO), JSON.toJSONString(lockMallPayOrderResponseDTOResponse));
    }

}
