package com.epam.esm.dto.orderDto;


import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.dto.userDto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadOrder extends RepresentationModel<ReadOrder> {

    private long id;
    UserDto user;
    List<ReadCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
