package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto){
        orderDto.setOrderId(UUID.randomUUID().toString());
        // 전체 금액 = 단가 * 수량
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        // orderEntity 타입으로 바꿔 데이터베이스에 저장장
       ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //orderDto 값을 orderEntity로 바꾼다
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        // 최종적으로 orderEntity 값을 orderDto로 바꾸겠다
        OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);

        return returnValue;
    }

    //단일값 가지고 오는 메서드 (해당 주문을 찾아 오는)
    @Override
    public OrderDto getOrderByOrderId(String orderId){
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return null;
    }


    @Override
    // 해당 사용자의 주문내역만 검색해 오겠다.
    public Iterable<OrderEntity> getOrdersByUserId(String userId){
        return orderRepository.findByUserId(userId);
    }
}
