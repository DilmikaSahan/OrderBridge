package com.Order.Order.common;

import com.Order.Order.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class SuccessOrderResponse implements OrderResponse{
    @JsonUnwrapped
    private final OrderDto order;

    public SuccessOrderResponse(OrderDto order){
        this.order=order;
    }
}
