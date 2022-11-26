package com.epam.esm.mapper.impl.orderMapper;


import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionOrderFromCreateModel implements Mapper<CreateOrder, Order> {

    private final TransitionUserFromUserDto userMapper;
    private final TransitionCertificateFromCertificateDto certificateMapper;

    @Override
    public Order mapFrom(CreateOrder object) {
        return Order.builder()
                .user(userMapper.mapFrom(object.getUser()))
                .certificates(List.of(certificateMapper.mapFrom(object.getCertificate())))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }
}
