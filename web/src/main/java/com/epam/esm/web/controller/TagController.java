package com.epam.esm.web.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagServiceImpl service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getTag(@PathVariable long id) {
        Tag tag = service.findById(id);
        return tag;
    }

}
