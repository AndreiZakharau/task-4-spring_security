package com.epam.esm.service.impl;

import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.dto.tagDto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.tagMapper.TransitionReadTagFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagDtoFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromCreateTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromTagDto;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.TagsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagsValidator tagsValidator;
    private final CertificateRepository certificateRepository;
    private final TransitionReadTagFromTag readMapper;
    private final TransitionTagDtoFromTag tagDtoFromTag;
    private final LanguageMassage languageMassage;
    private final TransitionTagFromCreateTag createTagMapper;
    private final TransitionTagFromTagDto tagFromTagDto;


    @Transactional
    @Override
    public Page<ReadTag> getAllEntity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tags = tagRepository.findAll(pageable);
        return tags.map(readMapper::mapFrom);
    }


    @Transactional
    @Override
    public Page<TagDto> getAllTag(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tags = tagRepository.findAll(pageable);
        return tags.map(tagDtoFromTag::mapFrom);
    }

    @Override
    @Transactional
    public void saveEntity(CreateTag createTag) {
        Tag tag = createTagMapper.mapFrom(createTag);
        if (tagsValidator.isValid(tag)) {
            tagRepository.save(tag);
        } else {
            throw new IncorrectDataException("message.not.valid");
        }
    }

    @Override
    @Transactional
    public TagDto updateEntity(long id, TagDto tagDto) {
        Optional<Tag> tag = tagRepository.findById(id);
        Tag tag1;
        if (tag.isPresent()) {
            tag.get().setTagName(tagDto.getTagName());
            if (tagsValidator.isValidModel(tagFromTagDto.mapFrom(tagDto))) {
             tag1 = tagRepository.saveAndFlush(tag.get());
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
        return tagDtoFromTag.mapFrom(tag1);
    }



    @Override
    @Transactional
    public long countAll() {
        return tagRepository.count();
    }

    @Override
    @Transactional
    public Optional<ReadTag> findById(long id) {
        Optional<Tag> tag = Optional.of(tagRepository.findById(id)).orElseThrow();
        if (tag.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
        return tag.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if (tagRepository.findById(id).isPresent()) {
            tagRepository.deleteById(id);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
    }

    @Transactional
    @Override
    public ReadTag getPopularTagWithUser() {
        return readMapper.mapFrom(tagRepository.getPopularTagWithUser());
    }


    @Override
    @Transactional
    public void addTagToCertificate(long tId, long cId) {
        Certificate certificate = certificateRepository.getReferenceById(cId);
        Tag tag = tagRepository.getReferenceById(tId);
        certificate.addTag(tag);
    }
}
