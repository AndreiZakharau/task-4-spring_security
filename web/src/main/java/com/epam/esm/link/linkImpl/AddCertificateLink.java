package com.epam.esm.link.linkImpl;


import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.dto.tagDto.TagDto;
import com.epam.esm.link.AddAbstractLink;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class AddCertificateLink extends AddAbstractLink<ReadCertificate> {

    private final CertificateServiceImpl service;

    @Override
    public void addLinks(ReadCertificate readCertificate) {
        long id = readCertificate.getId();
        addIdLinks(CertificateController.class,readCertificate,id,SELF_LINK,UPDATE_LINK,DELETE_LINK);
        if(readCertificate.getTags()!=null){
            for (TagDto tagDto : readCertificate.getTags()) {
                tagDto.add(linkTo(TagController.class).slash(tagDto.getId()).withRel("tag"));
            }
        }
    }

    public void addLinks(CertificateDto certificateDto) {
        long id = certificateDto.getId();
        linkTo(CertificateController.class, certificateDto, id, SELF_LINK, UPDATE_LINK, DELETE_LINK);
    }

//    @Override
    public void pageLink(Page listPage, ReadCertificate readCertificate) {
        int totalRecords = listPage.getTotalPages();
        int pages = Pagination.findPages(totalRecords, listPage.getSize());
        int lastPage = Pagination.findLastPage(pages,listPage.getSize() , totalRecords);
        readCertificate.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findPrevPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("prev"));
        readCertificate.add(linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findNextPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("next"));
    }


    public void addCreateLink(CreateCertificate createCertificate) {
        String name = createCertificate.getCertificateName();
        createCertificate.add(linkTo(CertificateController.class).slash(name).withRel(SELF_LINK));
    }
}
