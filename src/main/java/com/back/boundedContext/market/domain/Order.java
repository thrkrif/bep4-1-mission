package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
public class Order extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;
    private LocalDateTime requestPaymentDate;
    private LocalDateTime paymentDate;
    private LocalDateTime cancelDate; // 추가됨
    private long price;
    private long salePrice;

    @OneToMany(mappedBy = "order",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getBuyer();
        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product){
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );

        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();
    }

    public void completePayment(){
        paymentDate = LocalDateTime.now();
    }

    public boolean isPaid(){
        return paymentDate != null;
    }

    public void requestPayment(long pgPaymentAmount){
        requestPaymentDate = LocalDateTime.now();

        publishEvent(
                new MarketOrderPaymentRequestedEvent(
                        toDto(),
                        pgPaymentAmount
                )
        );
    }

    public void cancelRequestPayment(){
        requestPaymentDate = null;
    }

    public boolean isCanceled() {
        return cancelDate != null;
    }

    public boolean isPaymentInProgress(){
        return requestPaymentDate != null &&
                paymentDate == null && cancelDate == null;
    }

    public OrderDto toDto() {
        return new OrderDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                buyer.getId(),
                buyer.getNickname(),
                price,
                salePrice,
                requestPaymentDate,
                paymentDate
        );
    }

}
