package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagDtoFromTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionReadCertificateFromCertificate implements Mapper<Certificate, ReadCertificate> {

    private final TransitionTagDtoFromTag tagReadMapper;

    @Override
    public ReadCertificate mapFrom(Certificate object) {
        return new ReadCertificate(
                object.getId(),
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate(),
                tagReadMapper.buildListOnlyTag(object.getTags())
        );
    }

    public List<ReadCertificate> buildListModelCertificates(List<Certificate> certificates) {
        List<ReadCertificate> list = new ArrayList<>();
        for (Certificate certificate : certificates) {
            ReadCertificate model = ReadCertificate.builder()
                    .id(certificate.getId())
                    .certificateName(certificate.getCertificateName())
                    .description(certificate.getDescription())
                    .price(certificate.getPrice())
                    .duration(certificate.getDuration())
                    .createDate(certificate.getCreateDate())
                    .lastUpdateDate(certificate.getLastUpdateDate())
                    .tags(tagReadMapper.buildListOnlyTag(certificate.getTags()))
                    .build();
            list.add(model);
        }
        return list;
    }
}
