package com.epam.esm.mapper.impl.orderMapper;


import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransitionOrderFromOrderDto implements Mapper<OrderDto, Order> {

    private final TransitionCertificateFromCertificateDto certificateMapper;

    @Override
    public Order mapFrom(OrderDto object) {
        return Order.builder()
                .id(object.getId())
                .certificates(certificateMapper.buildListCertificate(object.getCertificate()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }

    public List<Order> buildListOrder(List<OrderDto> orderDtoList){
        List<Order> list = new ArrayList<>();
        for(OrderDto orderDto :orderDtoList){
            Order o = Order.builder()
                    .id(orderDto.getId())
                    .certificates(certificateMapper.buildListCertificate(orderDto.getCertificate()))
                    .cost(orderDto.getCost())
                    .datePurchase(orderDto.getDatePurchase())
                    .build();
            list.add(o);
        }
        return list;
    }

}
