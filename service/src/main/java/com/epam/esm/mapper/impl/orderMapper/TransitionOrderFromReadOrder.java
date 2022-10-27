package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromReadCertificate;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionOrderFromReadOrder implements Mapper<ReadOrder, Order> {

    private final TransitionCertificateFromReadCertificate certificateMapper;
    private final TransitionUserFromUserDto dtoFromUser;

    @Override
    public Order mapFrom(ReadOrder object) {
        return Order.builder()
                .certificates(certificateMapper.buildListCertificateFromModelCertificate(object.getCertificates()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }


    public List<Order> buildListOrder(List<ReadOrder> readOrders) {
        List<Order>orderList = new ArrayList<>();
        for (ReadOrder readOrder : readOrders){
            Order order = Order.builder()
                    .id(readOrder.getId())
                    .user(dtoFromUser.mapFrom(readOrder.getUser()))
                    .cost(readOrder.getCost())
                    .certificates(certificateMapper.buildListCertificateFromModelCertificate(readOrder.getCertificates()))
                    .datePurchase(readOrder.getDatePurchase())
                    .build();
            orderList.add(order);
        }
        return orderList;
    }
}
