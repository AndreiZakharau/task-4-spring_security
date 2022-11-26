package com.epam.esm.mapper.impl.orderMapper;


import com.epam.esm.dto.orderDto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionOrderDtoFromOrder implements Mapper<Order, OrderDto> {

    private final TransitionCertificateDtoFromCertificate certificateMapper;

    @Override
    public OrderDto mapFrom(Order object) {
        return new OrderDto(
                object.getId(),
                certificateMapper.buildListCertificateDto(object.getCertificates()),
                object.getCost(),
                object.getDatePurchase()
        );
    }

    public List<OrderDto> buildListOrderDto(List<Order> orders){
        List<OrderDto>list = new ArrayList<>();
        for (Order order : orders){
            OrderDto od = OrderDto.builder()
                    .id(order.getId())
                    .certificate(certificateMapper.buildListCertificateDto(order.getCertificates()))
                    .cost(order.getCost())
                    .datePurchase(order.getDatePurchase())
                    .build();
            list.add(od);
        }
        return list;
    }
}
