package com.Order.Order.common;

import com.Order.Order.dto.OrderDto;
import lombok.Getter;

@Getter
public class SuccessOrderResponse implements OrderResponse{
    private final OrderDto order;

    public SuccessOrderResponse(OrderDto order){
        this.order=order;
    }
}
