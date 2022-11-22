package com.epam.esm.service.impl;

import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionReadCertificateFromCertificate;
import com.epam.esm.model.SortParamsContext;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository repository;
    private final CertificateValidator certificateValidator;
    private final TagRepository tagRepository;
    private final TransitionReadCertificateFromCertificate readMapper;
    private final TransitionCertificateFromCertificateDto certificateFromCertificateDto;
    private final TransitionCertificateFromCreateCertificate certificateFromCreateCertificate;
    private final TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;
    private final LanguageMassage languageMassage;

    @Override
    @Transactional
    public Page<ReadCertificate> getAllEntity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Certificate> list = repository.findAll(pageable);
        return list.map(readMapper::mapFrom);

    }

    @Override
    @Transactional
    public void saveEntity(CreateCertificate createCertificate) {
        Certificate certificate = certificateFromCreateCertificate.mapFrom(createCertificate);
        if (certificateValidator.isValid(certificate)) {
            if (certificate.getCreateDate() == null) {
                certificate.setCreateDate(LocalDateTime.now());
            }
            if (certificate.getLastUpdateDate() == null) {
                certificate.setLastUpdateDate(certificate.getCreateDate().plusDays(certificate.getDuration()));
            }
            repository.save(certificate);
        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
        }
    }

    @Override
    @Transactional
    public CertificateDto updateEntity(long id, CertificateDto certificateDto) {
        Optional<Certificate> c = repository.findById(id);
        Certificate certificate1;
        if (c.isPresent()) {
            certificateDto.setId(id);
            if (certificateDto.getCertificateName() == null)
                certificateDto.setCertificateName(c.get().getCertificateName());
            if (certificateDto.getDescription() == null)
                certificateDto.setDescription(c.get().getDescription());
            if (certificateDto.getPrice() <= 0)
                certificateDto.setPrice(c.get().getPrice());
            if (certificateDto.getDuration() <= 0) {
                certificateDto.setDuration(c.get().getDuration());
                certificateDto.setLastUpdateDate(LocalDateTime.now().plusDays(c.get().getDuration()));
            } else {
                certificateDto.setLastUpdateDate(LocalDateTime.now().plusDays(certificateDto.getDuration()));
            }
            if (certificateDto.getCreateDate() == null)
                certificateDto.setCreateDate(c.get().getCreateDate());
            if (certificateDto.getLastUpdateDate() == null)
                certificateDto.setLastUpdateDate(c.get().getLastUpdateDate());
            Certificate certificate = certificateFromCertificateDto.mapFrom(certificateDto);

            if (certificateValidator.isValid(certificate)) {
                certificate1 = repository.save(certificate);
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        }
        return certificateDtoFromCertificate.mapFrom(certificate1);
    }

    @Override
    @Transactional
    public Optional<ReadCertificate> findById(long id) {
        Optional<Certificate> c = repository.findById(id);
        if (c.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        }
        return c.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        Optional<Certificate> c = repository.findById(id);
        if (c.isPresent()) {
            repository.delete(c.get());
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        }
    }

    @Override
    @Transactional
    public long countAll() {
        return repository.count();
    }


    @Transactional
    @Override
    public Page<ReadCertificate> getCertificateByParameters(
            String certificateName, List<String> tagNames, String description, List<Double> prices,
            List<String> sortColumns, List<String> orderTypes, int page, int size) {

        if (certificateName != null) {
            certificateName = "%" + certificateName + "%";
        }
        if (description != null) {
            description = "%" + description + "%";
        }
        List<Tag> tags = new ArrayList<>();
        if (!tagNames.isEmpty()) {
            for (String name : tagNames) {
                Tag tag = tagRepository.findByTagName(name).orElseThrow();
                tags.add(tag);
            }
        }

        Sort sort = getSort(sortColumns, orderTypes);
        Pageable pageable = PageRequest.of(page, size, sort);
        double minPrice = getMinPrise(prices);
        double maxPrice = getMaxPrise(prices);
        Page<Certificate> list = repository.findAllDistinctByCertificateNameLikeOrDescriptionLikeOrPriceBetweenOrTagsIn(
                certificateName, description, minPrice, maxPrice, tags, pageable);

        return list.map(readMapper::mapFrom);
    }

    private Sort getSort(List<String> sortColumns, List<String> orderTypes) {

        List<String> typesList = Arrays.asList("ASC", "DESC");
        Sort sort = null;
        SortParamsContext sortParameters;
        if (sortColumns != null || !sortColumns.isEmpty()) {
            sortParameters = new SortParamsContext(sortColumns, orderTypes);

            if (!certificateValidator.columnsValid(sortParameters)) {
                throw new NoSuchEntityException(languageMassage.getMessage("message.bad.parameters"));
            } else {
                List<String> orderTypesList = sortParameters.getOrderTypes();
                for (String column : sortColumns) {
                    sort = Sort.by(column);
                    if (orderTypes.size() > 0 && orderTypes.stream().anyMatch(order -> typesList.contains(order.toUpperCase(Locale.ROOT)))) {
                        if (orderTypes.get(0).toUpperCase(Locale.ROOT).equals("DESC")) {
                            sort = Sort.by(column).descending();
                        } else {
                            sort = Sort.by(column).ascending();
                        }
                    }
                }
            }
        }
        return sort;
    }

    private double getMinPrise(List<Double> prices) {
        double minPrice = 0;
        if (prices != null) {
            minPrice = prices.stream().mapToDouble(price -> price)
                    .min().orElseThrow();
        }

        return minPrice;
    }

    private double getMaxPrise(List<Double> prices) {
        double maxPrice = 0;
        if (prices != null) {
            maxPrice = prices.stream().mapToDouble(price -> price)
                    .max().orElseThrow();
        }
        return maxPrice;
    }
}

