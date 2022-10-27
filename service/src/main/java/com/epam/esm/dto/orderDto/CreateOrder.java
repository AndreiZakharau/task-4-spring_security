package com.epam.esm.dto.orderDto;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import com.epam.esm.Dto.userDto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrder extends RepresentationModel<CreateOrder> {

    UserDto user;
    CertificateDto certificate;
    private double cost;
    public LocalDateTime datePurchase;
}
