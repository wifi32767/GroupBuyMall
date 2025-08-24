package com.wifi32767.test.interfaces;

import com.alibaba.fastjson.JSON;
import com.wifi32767.interfaces.dto.GoodsMallRequestDTO;
import com.wifi32767.interfaces.dto.GoodsMallResponseDTO;
import com.wifi32767.interfaces.http.MallIndexController;
import com.wifi32767.interfaces.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = com.wifi32767.app.Application.class)
public class MallIndexControllerTest {

    @Resource
    private MallIndexController mallIndexController;

    @Test
    public void test_queryGroupBuyMallConfig() {
        GoodsMallRequestDTO requestDTO = new GoodsMallRequestDTO();
        requestDTO.setSource("s01");
        requestDTO.setChannel("c01");
        requestDTO.setUserId("xfg01");
        requestDTO.setGoodsId("9890001");

        Response<GoodsMallResponseDTO> response = mallIndexController.queryGroupBuyMallConfig(requestDTO);

        log.info("请求参数:{}", JSON.toJSONString(requestDTO));
        log.info("应答结果:{}", JSON.toJSONString(response));
    }

}
