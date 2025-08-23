package com.wifi32767.interfaces.http;

import com.alibaba.fastjson.JSON;
import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.domain.activity.model.entity.*;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import com.wifi32767.domain.trade.service.TradeOrderService;
import com.wifi32767.interfaces.dto.LockMallPayOrderRequestDTO;
import com.wifi32767.interfaces.dto.LockMallPayOrderResponseDTO;
import com.wifi32767.interfaces.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/trade/")
public class MallTradeController {

    @Resource
    private IndexGroupBuyMallService indexGroupBuyMallService;

    @Resource
    private TradeOrderService tradeOrderService;

    /**
     * 拼团营销锁单
     */
    @RequestMapping(value = "lock_mall_pay_order", method = RequestMethod.POST)
    public Response<LockMallPayOrderResponseDTO> lockMallPayOrder(LockMallPayOrderRequestDTO lockMallPayOrderRequestDTO) {
        try {
            // 参数
            String userId = lockMallPayOrderRequestDTO.getUserId();
            String source = lockMallPayOrderRequestDTO.getSource();
            String channel = lockMallPayOrderRequestDTO.getChannel();
            String goodsId = lockMallPayOrderRequestDTO.getGoodsId();
            Long activityId = lockMallPayOrderRequestDTO.getActivityId();
            String outTradeNo = lockMallPayOrderRequestDTO.getOutTradeNo();
            String teamId = lockMallPayOrderRequestDTO.getTeamId();

            log.info("营销交易锁单:{} LockMallPayOrderRequestDTO:{}", userId, JSON.toJSONString(lockMallPayOrderRequestDTO));

            if (StringUtils.isBlank(userId) || StringUtils.isBlank(source) || StringUtils.isBlank(channel) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(goodsId) || null == activityId) {
                return Response.<LockMallPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }

            // 查询 outTradeNo 是否已经存在交易记录
            MallPayOrderEntity mallPayOrderEntity = tradeOrderService.queryNoPayMallPayOrderByOutTradeNo(userId, outTradeNo);
            if (null != mallPayOrderEntity) {
                LockMallPayOrderResponseDTO lockMallPayOrderResponseDTO = LockMallPayOrderResponseDTO.builder()
                        .orderId(mallPayOrderEntity.getOrderId())
                        .deductionPrice(mallPayOrderEntity.getDeductionPrice())
                        .tradeOrderStatus(mallPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                        .build();

                log.info("交易锁单记录(存在):{} mallPayOrderEntity:{}", userId, JSON.toJSONString(mallPayOrderEntity));
                return Response.<LockMallPayOrderResponseDTO>builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .info(ResponseCode.SUCCESS.getInfo())
                        .data(lockMallPayOrderResponseDTO)
                        .build();
            }

            // 判断拼团锁单是否完成了目标
            if (null != teamId) {
                GroupBuyProgressVO groupBuyProgressVO = tradeOrderService.queryGroupBuyProgress(teamId);
                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getTargetCount(), groupBuyProgressVO.getLockCount())) {
                    log.info("交易锁单拦截-拼单目标已达成:{} {}", userId, teamId);
                    return Response.<LockMallPayOrderResponseDTO>builder()
                            .code(ResponseCode.E0006.getCode())
                            .info(ResponseCode.E0006.getInfo())
                            .build();
                }
            }

            // 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMallService.indexMallTrial(MallProductEntity.builder()
                    .userId(userId)
                    .source(source)
                    .channel(channel)
                    .goodsId(goodsId)
                    .activityId(activityId)
                    .build());
            // 人群限定
            if (!trialBalanceEntity.getIsVisible() || !trialBalanceEntity.getIsEnable()) {
                return Response.<LockMallPayOrderResponseDTO>builder()
                        .code(ResponseCode.E0007.getCode())
                        .info(ResponseCode.E0007.getInfo())
                        .build();
            }

            GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

            // 锁单
            mallPayOrderEntity = tradeOrderService.lockMallPayOrder(
                    UserEntity.builder().userId(userId).build(),
                    PayActivityEntity.builder()
                            .teamId(teamId)
                            .activityId(activityId)
                            .activityName(groupBuyActivityDiscountVO.getActivityName())
                            .startTime(groupBuyActivityDiscountVO.getStartTime())
                            .endTime(groupBuyActivityDiscountVO.getEndTime())
                            .targetCount(groupBuyActivityDiscountVO.getTarget())
                            .build(),
                    PayDiscountEntity.builder()
                            .source(source)
                            .channel(channel)
                            .goodsId(goodsId)
                            .goodsName(trialBalanceEntity.getGoodsName())
                            .originalPrice(trialBalanceEntity.getOriginalPrice())
                            .deductionPrice(trialBalanceEntity.getDeductionPrice())
                            .payPrice(trialBalanceEntity.getPayPrice())
                            .outTradeNo(outTradeNo)
                            .build());

            log.info("交易锁单记录(新):{} mallPayOrderEntity:{}", userId, JSON.toJSONString(mallPayOrderEntity));

            // 返回结果
            return Response.<LockMallPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(LockMallPayOrderResponseDTO.builder()
                            .orderId(mallPayOrderEntity.getOrderId())
                            .deductionPrice(mallPayOrderEntity.getDeductionPrice())
                            .tradeOrderStatus(mallPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("营销交易锁单业务异常:{} LockMallPayOrderRequestDTO:{}", lockMallPayOrderRequestDTO.getUserId(), JSON.toJSONString(lockMallPayOrderRequestDTO), e);
            return Response.<LockMallPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("营销交易锁单服务失败:{} LockMallPayOrderRequestDTO:{}", lockMallPayOrderRequestDTO.getUserId(), JSON.toJSONString(lockMallPayOrderRequestDTO), e);
            return Response.<LockMallPayOrderResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
