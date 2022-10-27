package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public List<Tag> getAllEntity(int limit, int offset) {
        return null;
    }

    @Transactional
    @Override
    public void saveEntity(Object o) {

    }

    @Transactional
    @Override
    public void updateEntity(long id, Object o) {

    }

    @Transactional
    @Override
    public Tag findById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.get();
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {

    }

    @Override
    public long countAll() {
        return 0;
    }
}
