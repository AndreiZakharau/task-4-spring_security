package com.epam.esm.service;

import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.dto.tagDto.TagDto;
import org.springframework.data.domain.Page;

public interface TagService extends EntityService<ReadTag, CreateTag, TagDto> {

    Page<TagDto> getAllTag(int page, int size);

    ReadTag getPopularTagWithUser();

    void addTagToCertificate(long tId, long cId);
}
