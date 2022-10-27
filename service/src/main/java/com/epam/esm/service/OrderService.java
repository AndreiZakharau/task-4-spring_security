package com.epam.esm.service;


import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OrderService extends EntityService<ReadOrder, CreateOrder, OrderDto>{


    @Transactional
    List<ReadOrder> getOrdersByUserId(long userId, int limit, int offset);
}
