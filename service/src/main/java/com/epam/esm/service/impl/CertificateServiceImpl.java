package com.epam.esm.service.impl;

import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCertificateDto;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromReadCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionReadCertificateFromCertificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<ReadCertificate> getAllEntity(int limit, int offset) {
//        List<Certificate> list = repository.findAll(limit, offset); //Todo
//        return readMapper.buildListModelCertificates(list);
        return  null;

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


//    @Transactional
//    public void autoSaveCertificatesTag() {
//
//        List<Tag> tagList = tagRepository.getTags();
//        for (Tag t : tagList) {
//            List<Certificate> certificateList = repository.getCertificatesByName(t.getTagName());
//            if (!certificateList.isEmpty()) {
//                for (Certificate c : certificateList) {
//                    if (repository.getCertificateAndTag(c, t).isEmpty()) {
//                        repository.saveCertificatesTag(c.getId(), t.getId());
//                    }
//                }
//            }
//        }
//    }

//    @Transactional
//    public List<ReadCertificate> getCertificatesByTag(String tagName) {
//        List<Certificate> list;
//        Optional<Tag> tag = tagRepository.findByTagName(tagName);
//        if (tag.isEmpty()) {
//            throw new NoSuchEntityException(languageMassage.getMessage("message.be.empty"));
//        } else {
//            list = repository.getCertificatesByName(tagName);
//        }
//        return readMapper.buildListModelCertificates(list);
//    }

//    @Transactional
//    public List<ReadCertificate> getCertificateByName(String name) {
//        List<Certificate> list = repository.getCertificatesByName(name);
//        if (list.isEmpty()) {
//            throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
//        }
//        return readMapper.buildListModelCertificates(list);
//    }

//    @Transactional
//    @Override
//    public List<ReadCertificate> getCertificatesByTags(List<String> tagNames) {
//        List<String>list = new ArrayList<>();
//        for (String name : tagNames){
//            Optional<Tag> tag = tagRepository.getTagByName(name);
//            if(tag.isEmpty()){
//                throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
//            }
//            list.add(tag.get().getTagName());
//        }
//        return readMapper.buildListModelCertificates(repository.getCertificatesByTags(list));
//    }

//    @Transactional
//    @Override
//    public List<ReadCertificate> getCertificateByParameters(
//            String name, List<String> tagNames, String description, List<Double> price,
//            List<String> sortColumns, List<String> orderTypes, int offset, int size){
//        SortParamsContext sortParameters = null;
//        if (sortColumns != null) {
//            sortParameters = new SortParamsContext(sortColumns, orderTypes);
//            if(!certificateValidator.columnsValid(sortParameters)) {
//                throw new NoSuchEntityException("bad parameters");
//            }
//        }
//
//        return readMapper.buildListModelCertificates(repository.getCertificateByParameters(name,tagNames,description,price,sortColumns, orderTypes, offset,size));
//    }
}
