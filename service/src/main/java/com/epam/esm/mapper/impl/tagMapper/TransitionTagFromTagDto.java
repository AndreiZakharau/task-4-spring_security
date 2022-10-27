package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitionTagFromTagDto implements Mapper<TagDto, Tag> {
    @Override
    public Tag mapFrom(TagDto object) {
        return Tag.builder()
                .id(object.getId())
                .tagName(object.getTagName())
                .build();
    }

    public List<Tag> buildListTags(List<TagDto> tagDtos) {
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            Tag tag = Tag.builder()
                    .id(tagDto.getId())
                    .tagName(tagDto.getTagName())
                    .build();
            tags.add(tag);
        }
        return tags;
    }

}
