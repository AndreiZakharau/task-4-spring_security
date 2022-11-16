package com.epam.esm.service.impl;


import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.orderMapper.TransitionCreateOrderFromOrder;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;

import com.epam.esm.mapper.impl.orderMapper.TransitionReadOrderFromOrder;
import com.epam.esm.repository.OrderRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @Mock
    private OrderRepository repository;
    @Mock
    private TransitionReadOrderFromOrder readOrder;
    @Mock
    private TransitionOrderFromCreateOrder orderFromCreateOrder;
    @Mock
    private TransitionCreateOrderFromOrder createOrder;
    @InjectMocks
    private OrderServiceImpl orderService;


    private static final User USER_1 = User.builder()
            .id(3L)
            .nickName("Andrei")
            .email("andrei666@gmail.com")
            .build();
    private static final Certificate CERTIFICATE_1 = Certificate.builder()
            .id(1L)
            .certificateName("coffee")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(3.5)
            .duration(30)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(30))
            .build();
    private static final Certificate CERTIFICATE_2 = Certificate.builder()
            .id(2L)
            .certificateName("tea")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(1.5)
            .duration(30)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(30))
            .build();
    private static final Order ORDER_1 = Order.builder()
            .id(1L)
            .user(USER_1)
            .certificates(List.of(CERTIFICATE_1))
            .cost(CERTIFICATE_1.getPrice())
            .datePurchase(LocalDateTime.now())
            .build();
    private static final Order ORDER_2 = Order.builder()
            .id(2L)
            .user(USER_1)
            .certificates(List.of(CERTIFICATE_2))
            .cost(CERTIFICATE_2.getPrice())
            .datePurchase(LocalDateTime.now())
            .build();
    private static final List<Order> LIST_ORDERS = List.of(ORDER_1, ORDER_2);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllEntity() {

        when(repository.findAll((Pageable) any())).thenReturn(Page.empty());
        orderService.getAllEntity(0, 10);
        verify(repository).findAll((Pageable) any());

    }

    @Test
    void saveEntity() {
        Order order = Order.builder().id(3L)
                .certificates(List.of(CERTIFICATE_2))
                .cost(CERTIFICATE_2.getPrice())
                .user(USER_1).datePurchase(LocalDateTime.now())
                .build();
        List<Order> list = new ArrayList<>(LIST_ORDERS);
        assertEquals(list.size(), 2);
        when(repository.save(order)).thenReturn(order);
        Order order3 = repository.save(order);
        list.add(order3);
        verify(repository, times(1)).save(order);
        assertEquals(list.size(), 3);

    }


    @Test
    void findById() {
        assertEquals(ORDER_1.getId(), 1);
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER_1));
        Optional<Order> order = repository.findById(1L);
        verify(repository, times(1)).findById(anyLong());
        assertEquals(order.get().getId(), ORDER_1.getId());

    }

    @Test
    void deleteEntity() {

        doNothing().when(repository).deleteById(ORDER_1.getId());
        repository.deleteById(1L);
        verify(repository, times(1)).deleteById(any());
    }


    @Test
    void getOrdersByUserId() {

        when(repository.getOrdersByUserId(USER_1.getId())).thenReturn(LIST_ORDERS);
        List<Order> orders = repository.getOrdersByUserId(3L);
        verify(repository, times(1)).getOrdersByUserId(anyLong());
        assertEquals(orders.size(), 2);
    }
}