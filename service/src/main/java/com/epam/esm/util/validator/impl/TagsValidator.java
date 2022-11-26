package com.epam.esm.util.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.validator.Validator;
import org.springframework.stereotype.Service;

@Service("tagsValidator")
public class TagsValidator implements Validator<Tag> {

    private static final int MIN_LETTERS = 2;
    private static final int MAX_LETTERS = 60;

    private static final String TEXT_PATTERN = "[^\s]+[A-Za-z0-9-\s'\"-.]*";

    @Override
    public boolean isValid(Tag tag) {
        return isNameValid(tag.getTagName());
    }

    public boolean isNameValid(String name) {
        if (name == null) {
            return false;
        } else
            return name.length() >= MIN_LETTERS && name.length() <= MAX_LETTERS && name.matches(TEXT_PATTERN);
    }

    public boolean isValidModel(Tag tag) {
        return isNameValid(tag.getTagName());
    }

    public boolean isNameValidModel(String name) {
        if (name == null) {
            return false;
        } else
            return name.length() >= MIN_LETTERS && name.length() <= MAX_LETTERS && name.matches(TEXT_PATTERN);
    }
}
