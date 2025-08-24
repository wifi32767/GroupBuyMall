package com.wifi32767.interfaces.http;

import com.alibaba.fastjson.JSON;
import com.wifi32767.common.enums.ResponseCode;
import com.wifi32767.common.exceptions.AppException;
import com.wifi32767.domain.activity.model.entity.*;
import com.wifi32767.domain.activity.model.valobject.GroupBuyActivityDiscountVO;
import com.wifi32767.domain.activity.model.valobject.GroupBuyProgressVO;
import com.wifi32767.domain.activity.service.IndexGroupBuyMallService;
import com.wifi32767.domain.trade.model.entity.PayDiscountEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySettlementEntity;
import com.wifi32767.domain.trade.model.entity.TradePaySuccessEntity;
import com.wifi32767.domain.trade.service.TradeLockOrderService;
import com.wifi32767.domain.trade.service.settlement.TradeSettlementOrderService;
import com.wifi32767.interfaces.dto.LockMallPayOrderRequestDTO;
import com.wifi32767.interfaces.dto.LockMallPayOrderResponseDTO;
import com.wifi32767.interfaces.dto.SettlementMallPayOrderRequestDTO;
import com.wifi32767.interfaces.dto.SettlementMallPayOrderResponseDTO;
import com.wifi32767.interfaces.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private TradeLockOrderService tradeLockOrderService;
    @Resource
    private TradeSettlementOrderService tradeSettlementOrderService;

    /**
     * 拼团营销锁单
     */
    @RequestMapping(value = "lock_mall_pay_order", method = RequestMethod.POST)
    public Response<LockMallPayOrderResponseDTO> lockMallPayOrder(LockMallPayOrderRequestDTO requestDTO) {
        try {
            // 参数
            String userId = requestDTO.getUserId();
            String source = requestDTO.getSource();
            String channel = requestDTO.getChannel();
            String goodsId = requestDTO.getGoodsId();
            Long activityId = requestDTO.getActivityId();
            String outTradeNo = requestDTO.getOutTradeNo();
            String teamId = requestDTO.getTeamId();
            String notifyUrl = requestDTO.getNotifyUrl();

            log.info("营销交易锁单:{} LockMallPayOrderRequestDTO:{}", userId, JSON.toJSONString(requestDTO));

            if (StringUtils.isBlank(userId) || StringUtils.isBlank(source) || StringUtils.isBlank(channel) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(goodsId) || null == activityId || StringUtils.isBlank(notifyUrl)) {
                return Response.<LockMallPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }

            // 查询 outTradeNo 是否已经存在交易记录
            MallPayOrderEntity mallPayOrderEntity = tradeLockOrderService.queryNoPayMallPayOrderByOutTradeNo(userId, outTradeNo);
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
            if (!StringUtils.isBlank(teamId)) {
                GroupBuyProgressVO groupBuyProgressVO = tradeLockOrderService.queryGroupBuyProgress(teamId);
                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getTargetCount(), groupBuyProgressVO.getLockCount())) {
                    log.info("交易锁单拦截-拼单目标已达成:{} {}", userId, teamId);
                    return Response.<LockMallPayOrderResponseDTO>builder()
                            .code(ResponseCode.E006.getCode())
                            .info(ResponseCode.E006.getInfo())
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
                        .code(ResponseCode.E007.getCode())
                        .info(ResponseCode.E007.getInfo())
                        .build();
            }

            GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

            // 锁单
            mallPayOrderEntity = tradeLockOrderService.lockMallPayOrder(
                    UserEntity.builder().userId(userId).build(),
                    PayActivityEntity.builder()
                            .teamId(teamId)
                            .activityId(activityId)
                            .activityName(groupBuyActivityDiscountVO.getActivityName())
                            .startTime(groupBuyActivityDiscountVO.getStartTime())
                            .endTime(groupBuyActivityDiscountVO.getEndTime())
                            .validTime(groupBuyActivityDiscountVO.getValidTime())
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
                            .notifyUrl(notifyUrl)
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
            log.error("营销交易锁单业务异常:{} LockMallPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMallPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("营销交易锁单服务失败:{} LockMallPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMallPayOrderResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @RequestMapping(value = "settlement_mall_pay_order", method = RequestMethod.POST)
    public Response<SettlementMallPayOrderResponseDTO> settlementMallPayOrder(@RequestBody SettlementMallPayOrderRequestDTO requestDTO) {
        try {
            log.info("营销交易组队结算开始:{} outTradeNo:{}", requestDTO.getUserId(), requestDTO.getOutTradeNo());

            if (StringUtils.isBlank(requestDTO.getUserId()) || StringUtils.isBlank(requestDTO.getSource()) || StringUtils.isBlank(requestDTO.getChannel()) || StringUtils.isBlank(requestDTO.getOutTradeNo()) || null == requestDTO.getOutTradeTime()) {
                return Response.<SettlementMallPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }

            // 1. 结算服务
            TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMallPayOrder(TradePaySuccessEntity.builder()
                    .source(requestDTO.getSource())
                    .channel(requestDTO.getChannel())
                    .userId(requestDTO.getUserId())
                    .outTradeNo(requestDTO.getOutTradeNo())
                    .outTradeTime(requestDTO.getOutTradeTime())
                    .build());

            SettlementMallPayOrderResponseDTO responseDTO = SettlementMallPayOrderResponseDTO.builder()
                    .userId(tradePaySettlementEntity.getUserId())
                    .teamId(tradePaySettlementEntity.getTeamId())
                    .activityId(tradePaySettlementEntity.getActivityId())
                    .outTradeNo(tradePaySettlementEntity.getOutTradeNo())
                    .build();

            // 返回结果
            Response<SettlementMallPayOrderResponseDTO> response = Response.<SettlementMallPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(responseDTO)
                    .build();

            log.info("营销交易组队结算完成:{} outTradeNo:{} response:{}", requestDTO.getUserId(), requestDTO.getOutTradeNo(), JSON.toJSONString(response));

            return response;
        } catch (AppException e) {
            log.error("营销交易组队结算异常:{} LockMallPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMallPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("营销交易组队结算失败:{} LockMallPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMallPayOrderResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
