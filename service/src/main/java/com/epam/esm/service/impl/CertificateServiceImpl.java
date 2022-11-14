package com.epam.esm.service.impl;

import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromReadCertificate;
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
    private final TransitionCertificateFromReadCertificate certificateFromReadCertificate;
    private final TransitionCertificateFromCertificateDto certificateFromCertificateDto;
    private final TransitionCertificateFromCreateCertificate certificateFromCreateCertificate;
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
    public void updateEntity(long id, CertificateDto certificateDto) {
        Optional<Certificate> c = repository.findById(id);
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
                repository.save(certificate);
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
        }
    }

    @Override
    @Transactional
    public Optional<ReadCertificate> findById(long id) {
        Optional<Certificate> c = repository.findById(id);
        if (c.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
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
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
        }
    }

    @Override
    @Transactional
    public long countAll() {
        return repository.count();
    }




    @Transactional
    @Override     //TODO sorting not working(working only asc id), bad sorting with only price. If all parameters - ok.
    public Page<ReadCertificate> getCertificateByParameters(
            String name, List<String> tagNames, String description, List<Double> prices,
            List<String> sortColumns, List<String> orderTypes, int page, int size){
        Sort sort = Sort.unsorted();
        List<String> typesList = Arrays.asList("ASC", "DESC");
        SortParamsContext sortParameters;
        if (sortColumns != null) {
            sortParameters = new SortParamsContext(sortColumns, orderTypes);
            if(!certificateValidator.columnsValid(sortParameters)) {
                throw new NoSuchEntityException("bad parameters"); //todo
            }
            List<String> orderTypesList = sortParameters.getOrderTypes();
            for (String c : sortColumns) {
                Sort newSort = Sort.by(c);
                if (orderTypes.stream().anyMatch(order -> typesList.contains(order.toUpperCase(Locale.ROOT)))) {
                    if(c.equals("DESC")){
                        newSort.descending();
                    }else {
                        newSort.ascending();
                    }

                }
            }
//            for (int i = 0; i < sortParameters.getSortColumns().size(); i++) {
//                String sortColumn = sortParameters.getSortColumns().get(i);
//                Sort newSort = Sort.by(sortColumn);
//                if (orderTypesList.size() <= i
//                        || orderTypesList.get(i).equalsIgnoreCase("ASC")) {
//                    newSort.ascending();
//                } else {
//                    newSort.descending();
//                }
//                sort.and(newSort);
//            }
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Certificate> list = null;
        if (name != null || !name.isEmpty()) {
            name ="%" + name + "%";

        }
        if (description != null || !description.isEmpty()) {
            description = "%" + description + "%";
        }

        double minPrice = 0;
        double maxPrice = 0;
        if (!prices.isEmpty()) {
            maxPrice = prices.stream().mapToDouble(price -> price)
                    .max().orElseThrow();
            minPrice = prices.stream().mapToDouble(price -> price)
                    .min().orElseThrow();

            list = repository.findAllByCertificateNameLikeAndDescriptionLikeAndPriceBetween(
                    name, description, minPrice, maxPrice, pageable
            );
        }

        return list.map(readMapper::mapFrom);
    }
}
