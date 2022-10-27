package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitionCertificateDtoFromCertificate implements Mapper<Certificate, CertificateDto> {

    @Override
    public CertificateDto mapFrom(Certificate object) {
        return new CertificateDto(
                object.getId(),
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate()
        );
    }

    public List<CertificateDto> buildListCertificateDto(List<Certificate> certificates){
        List<CertificateDto> list = new ArrayList<>();
        for(Certificate c : certificates){
            CertificateDto cd = CertificateDto.builder()
                    .id(c.getId())
                    .certificateName(c.getCertificateName())
                    .description(c.getDescription())
                    .price(c.getPrice())
                    .duration(c.getDuration())
                    .createDate(c.getCreateDate())
                    .lastUpdateDate(c.getLastUpdateDate())
                    .build();
            list.add(cd);
        }
        return list;
    }
}
