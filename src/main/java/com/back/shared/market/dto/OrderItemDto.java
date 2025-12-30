package com.back.shared.market.dto;

import com.back.standard.modelType.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderItemDto implements CanGetModelTypeCode {
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int orderId;
    private final int buyerId;
    private final String buyerName;
    private final int sellerId;
    private final String sellerName;
    private final int productId;
    private final String productName;
    private long price;
    private long salePrice;
    private double payoutRate; // 수수료(%)
    private final long payoutFee; // 수수료
    private final long salePriceWithoutFee; // 수수료를 제외한 판매자에게 보내는 금액

    @Override
    public String getModelTypeCode() {
        return "OrderItem";
    }
}
