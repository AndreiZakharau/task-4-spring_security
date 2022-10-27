package com.epam.esm.dto.orderDto;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {


    private long id;
    List<CertificateDto> certificate;
    private double cost;
    public LocalDateTime datePurchase;
}
