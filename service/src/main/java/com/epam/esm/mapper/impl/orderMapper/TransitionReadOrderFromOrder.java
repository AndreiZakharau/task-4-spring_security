package com.epam.esm.mapper.impl.orderMapper;


import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionReadCertificateFromCertificate;
import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionReadOrderFromOrder implements Mapper<Order, ReadOrder> {

    private final TransitionReadCertificateFromCertificate certificate;
    private final TransitionUserDtoFromUser fromUser;

    @Override
    public ReadOrder mapFrom(Order object) {
        return new ReadOrder(
                object.getId(),
                fromUser.mapFrom(object.getUser()),
                certificate.buildListModelCertificates(object.getCertificates()),
                object.getCost(),
                object.getDatePurchase()
        );
    }

    public List<ReadOrder> buildReadOrderModel(List<Order> orders) {
        List<ReadOrder> list = new ArrayList<>();
        for (Order order : orders) {
            ReadOrder readOrder = ReadOrder.builder()
                    .id(order.getId())
                    .certificates(certificate.buildListModelCertificates(order.getCertificates()))
                    .cost(order.getCost())
                    .datePurchase(order.getDatePurchase())
                    .build();
            list.add(readOrder);
        }
        return list;
    }
}
