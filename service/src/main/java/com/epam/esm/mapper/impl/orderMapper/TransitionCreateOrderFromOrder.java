package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransitionCreateOrderFromOrder implements Mapper<Order, CreateOrder> {

    private final TransitionCertificateDtoFromCertificate certificateMapper;
    private final TransitionUserDtoFromUser fromUser;
    @Override
    public CreateOrder mapFrom(Order object) {
        return new CreateOrder(
                fromUser.mapFrom(object.getUser()),
                certificateMapper.mapFrom(object.getCertificates().get(0)),
                object.getCost(),
                object.datePurchase
        );
    }
}
