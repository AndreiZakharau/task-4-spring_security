package com.epam.esm.service;

import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.dto.tagDto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


public interface TagService extends EntityService<ReadTag, CreateTag, TagDto> {
    @Transactional
    Page<TagDto> getAllTag(int page, int size);

    @Transactional
    ReadTag getPopularTagWithUser();

    @Transactional
    void addTagToCertificate(long tId, long cId);
}
