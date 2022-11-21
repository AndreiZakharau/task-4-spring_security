package com.epam.esm.service;


import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CertificateService extends EntityService<ReadCertificate, CreateCertificate, CertificateDto> {

    Page<ReadCertificate> getCertificateByParameters(
            String name, List<String> tagNames, String description, List<Double> price,
            List<String> sortColumns, List<String> orderTypes, int page, int size);
}
