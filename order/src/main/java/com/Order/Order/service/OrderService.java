package com.Order.Order.service;

import com.Inventory.Inventory.dto.InventoryDto;
import com.Order.Order.common.ErrorOrderResponse;
import com.Order.Order.common.OrderResponse;
import com.Order.Order.common.SuccessOrderResponse;
import com.Order.Order.dto.OrderDto;
import com.Order.Order.model.Orders;
import com.Order.Order.repo.OrderRepo;
import com.Product.Product.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient,WebClient productWebClient,OrderRepo orderRepo,ModelMapper modelMapper) {
        this.inventoryWebClient=inventoryWebClient;
        this.productWebClient=productWebClient;
        this.orderRepo=orderRepo;
        this.modelMapper=modelMapper;
    }

    public List<OrderDto> getAllOrders(){
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDto>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDto orderDto){
        Integer itemId = orderDto.getItemId();

        try {
            InventoryDto inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDto.class)
                    .block();
            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDto productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();

            assert productResponse != null;
            if (inventoryResponse.getQuantity()>0){
                if (productResponse.getForSale()==1){
                    orderRepo.save(modelMapper.map(orderDto, Orders.class));
                }else {
                    return new ErrorOrderResponse("Product not fot sale");
                }
                orderRepo.save(modelMapper.map(orderDto, Orders.class));
                return new SuccessOrderResponse(orderDto);
            }else {
                return new ErrorOrderResponse("Item not available");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
