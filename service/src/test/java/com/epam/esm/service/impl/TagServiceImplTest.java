package com.epam.esm.service.impl;

import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.tagMapper.TransitionReadTagFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagDtoFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromCreateTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromTagDto;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.TagsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    @Mock private  TagRepository repository;
    @Mock private  TagsValidator tagsValidator;
    @Mock private  CertificateRepository certificateRepository;
    @Mock private  TransitionReadTagFromTag readMapper;
    @Mock private  TransitionTagDtoFromTag tagDtoFromTag;
    @Mock private  LanguageMassage languageMassage;
    @Mock private  TransitionTagFromCreateTag createTagMapper;
    @Mock private  TransitionTagFromTagDto tagFromTagDto;
    @InjectMocks TagServiceImpl service;

        private final static Tag TAG_1 = Tag.builder()
            .id(13L)
            .tagName("coffee")
            .build();
    private final static Tag TAG_2 = Tag.builder()
            .id(11L)
            .tagName("sale")
            .build();

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
        CreateTag createTag = CreateTag.builder().tagName("Tea").build();
        Tag tag = Tag.builder().id(7L)
                .tagName(createTag.getTagName()).build();
        when(repository.save(tag)).thenReturn(tag);
        when(tagsValidator.isValid(tag)).thenReturn(true);
        Tag saveTag = repository.save(tag);
        verify(repository, times(1)).save(tag);
        assertEquals(saveTag.getId(),7L);
        assertEquals(saveTag.getTagName(),"Tea");
    }

    @Test
    void updateEntity() {
        Tag tag = TAG_1;
        assertEquals(tag.getTagName(), "coffee");
        String name = "free";
        tag.setTagName(name);
        when(repository.saveAndFlush(tag)).thenReturn(tag);
        Tag updateTag = repository.saveAndFlush(tag);
        verify(repository, times(1)).saveAndFlush(tag);
        assertEquals((updateTag.getTagName()),name );
    }


    @Test
    void findById() {
        when(repository.findById(13L)).thenReturn(java.util.Optional.of(TAG_1));
        Optional<ReadTag> t = service.findById(13);
        verify(repository).findById(anyLong());
        assertNotNull(t);
    }

    @Test
    void deleteEntity() {
        when(repository.findById(13L)).thenReturn(Optional.of(TAG_1));
        service.deleteEntity(13);
        verify(repository).deleteById(anyLong());

    }

}