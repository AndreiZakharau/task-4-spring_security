package com.epam.esm.service.impl;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Transactional
    @Override
    public List<ReadOrder> getAllEntity(int limit, int offset) {
        return null;
    }

    @Transactional
    @Override
    public void saveEntity(CreateOrder createOrder) {

    }

    @Transactional
    @Override
    public void updateEntity(long id, OrderDto orderDto) {

    }

    @Transactional
    @Override
    public ReadOrder findById(long id) {
        return null;
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {

    }

    @Transactional
    @Override
    public long countAll() {
        return 0;
    }
}
