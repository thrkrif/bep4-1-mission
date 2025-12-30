package com.back.shared.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderItemDto {
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
    private double payoutRate;
}
