package com.epam.esm.service.impl;

import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionReadCertificateFromCertificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.CertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CertificateServiceImplTest {

    @Mock private  CertificateRepository repository;
    @Mock private  CertificateValidator certificateValidator;
    @Mock private  TagRepository tagRepository;
    @Mock private  TransitionReadCertificateFromCertificate readMapper;
    @Mock private  TransitionCertificateFromCertificateDto certificateFromCertificateDto;
    @Mock private  TransitionCertificateFromCreateCertificate certificateFromCreateCertificate;
    @Mock private  LanguageMassage languageMassage;
    @InjectMocks CertificateServiceImpl service;


    private static final Certificate CERTIFICATE_1 = Certificate.builder()
                .id(1L)
            .certificateName("coffee")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(1.5)
            .duration(30)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(30))
            .build();
    private static final Certificate CERTIFICATE_2 = Certificate.builder()
            .id(7L)
            .certificateName("free tea")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(0)
            .duration(10)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(10))
            .build();
    private static final List<Certificate> LIST = List.of(CERTIFICATE_1,CERTIFICATE_2);
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllEntity() {
        when(repository.findAll((Pageable) any())).thenReturn(Page.empty());
        repository.findAll((Pageable)any());
        verify(repository,times(1)).findAll((Pageable) any());
    }

    @Test
    void saveEntity() {
        Certificate certificate = CERTIFICATE_1;
        when(repository.save(certificate)).thenReturn(certificate);
        when(certificateValidator.isValid(certificate)).thenReturn(true);
        Certificate saveCertificate = repository.save(certificate);
        verify(repository).save(saveCertificate);
        assertEquals(saveCertificate.getCertificateName(), "coffee");
        assertEquals(saveCertificate.getDuration(), 30);
    }

    @Test
    void updateEntity() {
        Certificate certificate = CERTIFICATE_2;
        assertEquals(certificate.getCertificateName(), "free tea");
        String name = "sale 50%";
        certificate.setCertificateName(name);
        when(repository.save(certificate)).thenReturn(certificate);
        Certificate updateC  = repository.save(certificate);
        verify(repository, times(1)).save(updateC);
        assertEquals(updateC.getCertificateName(), name);
    }

    @Test
    void findById() {

        when(repository.findById(1L)).thenReturn(Optional.of(CERTIFICATE_1));
        Optional<ReadCertificate> modelCertificate = service.findById(1L);
        verify(repository).findById(anyLong());
        assertNotNull(modelCertificate);
    }

    @Test
    void deleteEntity() {
        when(repository.findById(2L)).thenReturn(Optional.of(CERTIFICATE_2));
        Optional<Certificate> c = repository.findById(2L);
        assertNotNull(c);
        doNothing().when(repository).deleteById(2L);
        service.deleteEntity(2);
        when(repository.findById(2L)).thenReturn(null);
        Optional<Certificate> c2 = repository.findById(2L);
        assertNull(c2);
        verify(repository,times(3)).findById(2L);

    }

}