package com.epam.esm.mapper.impl.tagMapper;


import com.epam.esm.dto.tagDto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitionTagDtoFromTag implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapFrom(Tag object) {
        return new TagDto(
                object.getId(),
                object.getTagName()
        );
    }

    public List<TagDto> buildListOnlyTag(List<Tag> tags) {
        List<TagDto> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagDto = TagDto.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
            tagList.add(tagDto);
        }
        return tagList;
    }
}
