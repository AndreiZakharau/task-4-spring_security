package com.epam.esm.controller;


import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.dto.tagDto.TagDto;
import com.epam.esm.link.linkImpl.AddTagLink;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagServiceImpl service;

    private final AddTagLink tagLink;

    /**
     * Created new tag
     *
     * @param createTag the createTag(Dto)
     * @return new createTag(Dto)
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTag addTag(@RequestBody CreateTag createTag) {
        service.saveEntity(createTag);
        tagLink.addLink(createTag);
        return createTag;
    }


    /**
     * @param page the page
     * @param size the size
     * @return List createTag(Dto Tag)
     */
    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> listTags(@RequestParam(value = "page",defaultValue = "0", required = false) Integer page,
                                            @RequestParam(value = "size",defaultValue = "10", required = false) Integer size) {

        TagDto tagDto = new TagDto();
        Page<TagDto> tags = (service.getAllTag(page, size));
        tagLink.pageLink(tags,tagDto);
        return CollectionModel.of(tags.stream().peek(tagLink::addTadDtoLink).collect(Collectors.toList()), tagDto.getLinks());
    }

    /**
     * Get readTag(Dto Tag) by id
     *
     * @param id the id
     * @return readTag(Dto Tag)
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadTag getTag(@PathVariable long id) {
        Optional<ReadTag> model = service.findById(id);
        tagLink.addLinks(Optional.ofNullable(model).get().orElseThrow());
        return model.get();
    }

    /**
     * update readTag(Dto Tag) by id
     *
     * @param tag the readTag(Dto Tag)
     * @param id  the id
     * @return the exposed readTag(Dto Tag)
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(@RequestBody TagDto tag, @PathVariable long id) {
        service.updateEntity(id, tag);
        tagLink.addTadDtoLink(tag);
        return tag;
    }

    /**
     * delete tag by id
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
    }

    /**
     * @param page the page
     * @param size the size
     * @return list readTag(Dto Tag)
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadTag> getAllTags(@RequestParam(value = "page",defaultValue = "0", required = false) Integer page,
                                               @RequestParam(value = "size",defaultValue = "10", required = false) Integer size) {

        ReadTag readTag = new ReadTag();
        Page<ReadTag> list = service.getAllEntity(page, size);
        tagLink.pageLink(list,readTag);
        return CollectionModel.of(list.stream().peek(tagLink::addLinks).collect(Collectors.toList()), readTag.getLinks());
    }

    /**
     * @return the popular readTag(Dto Tag) from the user
     * with the maximum sum of all order
     */
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public ReadTag getPopularTagWithUser() {
        ReadTag tag = service.getPopularTagWithUser();
        tagLink.addLinks(tag);
        return  tag;
    }

    /**
     * @param tId the tagId
     * @param cId the certificateId
     * @return string message
     */
    @PostMapping("/{tId}/certificates/{cId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTagToCertificate(@PathVariable long tId,
                                      @PathVariable long cId ) {
        service.addTagToCertificate(tId,cId);
        return "Tag added to certificate.";//todo ' massage'
    }


}
