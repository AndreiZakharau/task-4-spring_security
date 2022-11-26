package com.epam.esm.mapper.impl.tagMapper;


import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class TransitionTagFromCreateTag implements Mapper<CreateTag, Tag> {
    @Override
    public Tag mapFrom(CreateTag object) {
        return Tag.builder().tagName(object.getTagName()).build();
    }
}
