package com.back.boundedContext.market.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    // 서비스가 가져갈 돈
    public static long calculatePayoutFee(long salePrice, double payoutRate) {
        return salePrice - calculateSalePriceWithoutFee(salePrice, payoutRate);
    }

    // 수수료 떼고 판매자가 가져갈 돈 : salePrice(판매금액)
    public static long calculateSalePriceWithoutFee(long salePrice, double payoutRate) {
        return Math.round(salePrice * payoutRate / 100);
    }

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }
}
