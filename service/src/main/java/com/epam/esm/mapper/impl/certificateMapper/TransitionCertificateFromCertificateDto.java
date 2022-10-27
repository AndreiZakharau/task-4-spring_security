package com.epam.esm.mapper.impl.certificateMapper;


import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitionCertificateFromCertificateDto implements Mapper<CertificateDto, Certificate> {
    @Override
    public Certificate mapFrom(CertificateDto object) {
        return Certificate.builder()
                .id(object.getId())
                .certificateName(object.getCertificateName())
                .description(object.getDescription())
                .duration(object.getDuration())
                .price(object.getPrice())
                .createDate(object.getCreateDate())
                .lastUpdateDate(object.getLastUpdateDate())
                .build();
    }

    public List<Certificate> buildListCertificate(List<CertificateDto> certificates){
        List<Certificate> list = new ArrayList<>();
        for (CertificateDto certificateDto : certificates){
            Certificate certificate = Certificate.builder()
                    .id(certificateDto.getId())
                    .certificateName(certificateDto.getCertificateName())
                    .description(certificateDto.getDescription())
                    .duration(certificateDto.getDuration())
                    .price(certificateDto.getPrice())
                    .createDate(certificateDto.getCreateDate())
                    .lastUpdateDate(certificateDto.getLastUpdateDate())
                    .build();
            list.add(certificate);
        }
        return list;
    }
}
