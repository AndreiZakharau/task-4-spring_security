package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionCertificateFromReadCertificate implements Mapper<ReadCertificate, Certificate> {

    private final TransitionTagFromTagDto tagMapper;


    @Override
    public Certificate mapFrom(ReadCertificate object) {
        return Certificate.builder()
                .id(object.getId())
                .certificateName(object.getCertificateName())
                .description(object.getDescription())
                .duration(object.getDuration())
                .price(object.getPrice())
                .createDate(object.getCreateDate())
                .lastUpdateDate(object.getLastUpdateDate())
                .tags(tagMapper.buildListTags(object.getTags()))
                .build();
    }

    public List<Certificate> buildListCertificateFromModelCertificate(List<ReadCertificate> models) {
        List<Certificate> list = new ArrayList<>();
        for (ReadCertificate model : models) {
            Certificate certificate = Certificate.builder()
                    .id(model.getId())
                    .certificateName(model.getCertificateName())
                    .description(model.getDescription())
                    .duration(model.getDuration())
                    .price(model.getPrice())
                    .createDate(model.getCreateDate())
                    .lastUpdateDate(model.getLastUpdateDate())
                    .tags(tagMapper.buildListTags(model.getTags()))
                    .build();
            list.add(certificate);
        }
        return list;
    }
}
