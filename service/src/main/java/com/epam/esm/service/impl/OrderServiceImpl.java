package com.epam.esm.service.impl;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromOrderDto;
import com.epam.esm.mapper.impl.orderMapper.TransitionReadOrderFromOrder;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final TransitionReadOrderFromOrder readOrder;
    private final TransitionOrderFromCreateOrder orderFromCreateOrder;
    private final TransitionOrderFromOrderDto orderFromOrderDto;

    @Override
    @Transactional
    public List<ReadOrder> getAllEntity(int limit, int offset) {
//        List<Order>orders = repository.findAll(limit, offset); //Todo
//        return readOrder.buildReadOrderModel(orders);
        return null;
    }

    @Override
    @Transactional
    public void saveEntity(CreateOrder createOrder) {
        repository.save(orderFromCreateOrder.mapFrom(createOrder));
    }

    @Override
    @Transactional
    public void updateEntity(long id, OrderDto orderDto) {
//        if(repository.findById(id).isPresent()){
//            repository.save(orderFromOrderDto.mapFrom(orderDto));
//        } else {
//            throw new NoSuchEntityException("Order from id " +id +"is empty");
//        }
    }

    @Override
    @Transactional
    public Optional<ReadOrder> findById(long userId) {
        Optional<Order>order =repository.findById(userId);
        if(order.isEmpty())
            throw new NoSuchEntityException("Order from  id " +userId +" is empty."); //Todo
        return order.map(readOrder::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
        }
        throw new NoSuchEntityException("Order from id " +id +" is empty."); //Todo
    }

    @Override
    @Transactional
    public long countAll() {
        return repository.count();
    }

    @Override
    @Transactional
    public List<ReadOrder> getOrdersByUserId(long userId, int limit, int offset){
//        List<Order>orders =repository.findOrdersByUserId(userId,limit,offset); //Todo
//        if(orders.isEmpty()){
//            throw new NoSuchEntityException("Order from user id " +userId +" is empty."); //Todo
//
//        }
//        return readOrder.buildReadOrderModel(orders);
        return null;
    }
}
