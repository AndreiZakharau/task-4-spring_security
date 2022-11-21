package com.epam.esm.service;


import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;
import org.springframework.data.domain.Page;

public interface OrderService extends EntityService<ReadOrder, CreateOrder, OrderDto>{

    Page<ReadOrder> getOrdersByUserId(long userId, int page, int size);
}
