package com.Order.Order.service;

import com.Order.Order.dto.OrderDto;
import com.Order.Order.model.Orders;
import com.Order.Order.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDto> getAllOrders(){
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDto>>(){}.getType());
    }

    public OrderDto saveOrder(OrderDto orderDto){
        orderRepo.save(modelMapper.map(orderDto, Orders.class));
        return orderDto;
    }
    public OrderDto UpdateOrder(OrderDto orderDto){
        orderRepo.save(modelMapper.map(orderDto, Orders.class));
        return orderDto;
    }

    public String deleteOrder(Integer orderId){
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }
    public OrderDto getOrderByID(Integer orderId){
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order,OrderDto.class);
    }


}
