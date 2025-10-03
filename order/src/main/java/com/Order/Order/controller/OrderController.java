package com.Order.Order.controller;

import com.Order.Order.common.OrderResponse;
import com.Order.Order.dto.OrderDto;
import com.Order.Order.kafka.OrderProducer;
import com.Order.Order.service.OrderService;
import com.example.base.dto.OrderEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @GetMapping("/getOrders")
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{orderId}")
    public OrderDto getOrderById(@PathVariable Integer orderId){
        return orderService.getOrderByID(orderId);
    }
    @PostMapping("/addorder")
    public OrderResponse saveOrder(@RequestBody OrderDto orderDto){
        OrderEventDto orderEventDto = new OrderEventDto();
        orderEventDto.setMessage("Order is committed");
        orderEventDto.setStatus("pending");
        orderProducer.sendMessage(orderEventDto);
        return orderService.saveOrder(orderDto);
    }
    @PutMapping("/updateorder")
    public OrderDto updateOrder(@RequestBody OrderDto orderDto){
        return orderService.UpdateOrder(orderDto);
    }
    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }

}
