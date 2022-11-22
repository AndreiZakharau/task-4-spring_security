package com.epam.esm.service.impl;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderDtoFromOrder;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromOrderDto;
import com.epam.esm.mapper.impl.orderMapper.TransitionReadOrderFromOrder;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final TransitionReadOrderFromOrder readOrder;
    private final TransitionOrderFromCreateOrder orderFromCreateOrder;
    private final TransitionOrderFromOrderDto orderFromOrderDto;
    private final TransitionOrderDtoFromOrder orderDtoFromOrder;
    private final LanguageMassage languageMassage;

    @Override
    @Transactional
    public Page<ReadOrder> getAllEntity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = repository.findAll(pageable);
        return orders.map(readOrder::mapFrom);
    }

    @Override
    @Transactional
    public void saveEntity(CreateOrder createOrder) {
        repository.save(orderFromCreateOrder.mapFrom(createOrder));
    }

    @Override
    @Transactional
    public OrderDto updateEntity(long id, OrderDto orderDto) {
        Order order;
        if (repository.findById(id).isPresent()) {
            order = repository.save(orderFromOrderDto.mapFrom(orderDto));
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("massage.order.with.id ") + id +
                    languageMassage.getMessage("message.does.not"));
        }
        return orderDtoFromOrder.mapFrom(order);
    }

    @Override
    @Transactional
    public Optional<ReadOrder> findById(long userId) {
        Optional<Order> order = repository.findById(userId);
        if (order.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.order.with.id") + userId +
                    languageMassage.getMessage("message.does.not"));
        }
        return order.map(readOrder::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }
        throw new NoSuchEntityException(languageMassage.getMessage("message.order.with.id") + id +
                languageMassage.getMessage("message.does.not"));
    }

    @Override
    @Transactional
    public long countAll() {
        return repository.count();
    }

    @Override
    @Transactional
    public Page<ReadOrder> getOrdersByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = repository.findOrdersByUserId(userId, pageable);
        if (orders.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.order.with.id") + userId +
                    languageMassage.getMessage("message.does.not"));
        }
        return orders.map(readOrder::mapFrom);
    }
}
