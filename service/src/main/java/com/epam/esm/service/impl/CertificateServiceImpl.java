package com.epam.esm.service.impl;

import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Transactional
    @Override
    public List<ReadCertificate> getAllEntity(int limit, int offset) {
        return null;
    }

    @Transactional
    @Override
    public void saveEntity(CreateCertificate createCertificate) {

    }

    @Transactional
    @Override
    public void updateEntity(long id, CertificateDto certificateDto) {

    }

    @Transactional
    @Override
    public ReadCertificate findById(long id) {
        return null;
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {

    }

    @Transactional
    @Override
    public long countAll() {
        return 0;
    }
}
