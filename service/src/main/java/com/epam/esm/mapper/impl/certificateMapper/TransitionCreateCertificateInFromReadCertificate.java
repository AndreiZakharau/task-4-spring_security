package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.Dto.certificateDto.CreateCertificate;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class TransitionCreateCertificateInFromReadCertificate implements Mapper<ReadCertificate, CreateCertificate> {
    @Override
    public CreateCertificate mapFrom(ReadCertificate object) {
        return new CreateCertificate(
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate()
        );
    }
}
