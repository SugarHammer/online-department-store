package com.pojo;

import lombok.Data;

import java.math.BigDecimal;


/**
 *    该类用于定义Alipay所需参数
 *    （实现支付宝沙箱支付功能）
 */

@Data
public class AliPay {

    //省略其他的业务参数，如商品id、购买数量等

    //商品名称
    private String subject;

    //总金额
    private BigDecimal totalAmount = BigDecimal.ZERO;

    //商品订单号
    private String traceNo;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
}