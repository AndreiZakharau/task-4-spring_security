package com.epam.esm.link.linkImpl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.tagDto.CreateTag;
import com.epam.esm.dto.tagDto.ReadTag;
import com.epam.esm.dto.tagDto.TagDto;
import com.epam.esm.link.AddAbstractLink;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class AddTagLink extends AddAbstractLink<ReadTag> {

    private final TagServiceImpl service;

    @Override
    public void addLinks(ReadTag readTag) {
        long id = readTag.getId();
        addIdLinks(TagController.class, readTag, id, SELF_LINK, DELETE_LINK);
        if(readTag.getCertificate()!=null){
            for(CertificateDto certificateDto : readTag.getCertificate()){
                certificateDto.add(linkTo(CertificateController.class).slash(id).withRel("certificates"));
            }
        }
    }

    public void addTadDtoLink(TagDto tagDto) {
        long id = tagDto.getId();
        linkTo(TagController.class, tagDto, id, SELF_LINK, DELETE_LINK);
    }
    public void addLink(CreateTag createTag) {
        String tagName = createTag.getTagName();
        linkTo(TagController.class, createTag, tagName, SELF_LINK, DELETE_LINK);
    }

    @Override
    public void pageLink(Page listPage, ReadTag readTag) {
        int totalRecords = listPage.getTotalPages();
        int pages = Pagination.findPages(totalRecords, listPage.getSize());
        int lastPage = Pagination.findLastPage(pages,listPage.getSize() , totalRecords);
        readTag.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findPrevPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("prev"));
        readTag.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findNextPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("next"));
    }
    public void pageLink(Page listPage, TagDto tagDto) {
        int totalRecords = listPage.getTotalPages();
        int pages = Pagination.findPages(totalRecords, listPage.getSize());
        int lastPage = Pagination.findLastPage(pages,listPage.getSize() , totalRecords);
        tagDto.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findPrevPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("prev"));
        tagDto.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findNextPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("next"));
    }


}
