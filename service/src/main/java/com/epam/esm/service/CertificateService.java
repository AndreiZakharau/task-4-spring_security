package com.epam.esm.service;


import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;


public interface CertificateService extends EntityService<ReadCertificate, CreateCertificate, CertificateDto> {


}
