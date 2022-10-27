package com.epam.esm.service;


import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;


public interface OrderService extends EntityService<ReadOrder, CreateOrder, OrderDto>{


}
