package com.epam.esm.controller;


import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.link.linkImpl.AddCertificateLink;
import com.epam.esm.service.impl.CertificateServiceImpl;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateServiceImpl service;
    private final AddCertificateLink certificateLink;

    /**
     * created certificate
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     *
     * @param certificate the CreateCertificate (certificate Dto)
     * @return new createCertificate (certificate Dto)
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCertificate addCertificate(@RequestBody CreateCertificate certificate) {
        service.saveEntity(certificate);
        certificateLink.addCreateLink(certificate);
        return certificate;
    }


    /**
     * @param page the page
     * @param size the size
     * @return list ReadCertificate (certificate Dto)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadCertificate> listAllCertificates(@RequestParam(value = "page",defaultValue = "0", required = false) Integer page,
                                                                @RequestParam(value = "size",defaultValue = "10", required = false) Integer size) {

        ReadCertificate readCertificate = new ReadCertificate();
        Page<ReadCertificate> list = service.getAllEntity(page, size);
        certificateLink.pageLink(list, readCertificate);

        return CollectionModel.of(list.stream()
                .peek(certificateLink::addLinks)
                .collect(Collectors.toList()), readCertificate.getLinks());
    }


    /**
     * get certificateDto by id
     *
     * @param id the id
     * @return ReadCertificate (certificate Dto)
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadCertificate> getCertificateById(@PathVariable("id") Long id) {
        Optional<ReadCertificate> model = Optional.ofNullable(service.findById(id)).get();
        certificateLink.addLinks(model.get());
        return model;
    }

    /**
     * update certificate
     * enter the fields that you want to update
     * date not updated except the date
     *
     * @param certificate the ReadCertificate (certificate Dto)
     * @param id          the id
     * @return updated ReadCertificate (certificate Dto)
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@RequestBody CertificateDto certificate, @PathVariable long id) {
        service.updateEntity(id, certificate);
        certificateLink.addLinks(certificate);
        return certificate;
    }

    /**
     * delete certificate by id
     *
     * @param id the id
     * @return HttpStatus
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        service.deleteEntity(id);

    }


    /**
     * @param certificateName the certificate name
     * @param tagNames the certificate tagNames
     * @param description the certificate description
     * @param price the certificate price
     * @param sortColumns the certificate column
     * @param orderTypes the type (asc or desc)
     * @param page the page
     * @param size the size
     * @return list certificates
     */
    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadCertificate> getCertificateByParameters(
            @RequestParam(value = "certificateName",required = false) String certificateName,
            @RequestParam(value = "tagName",defaultValue = "", required = false) List<String> tagNames,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) List<Double> price,
            @RequestParam(value = "sort",  defaultValue = "id" ,required = false) List<String> sortColumns,
            @RequestParam(value = "order" ,defaultValue = "asc" ,required = false) List<String> orderTypes,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        ReadCertificate readCertificate = new ReadCertificate();
        Page<ReadCertificate> list = service.getCertificateByParameters(certificateName, tagNames, description, price,sortColumns,orderTypes, page, size );
        certificateLink.pageLink(list,readCertificate);
        return CollectionModel.of(list.stream()
                .peek(certificateLink::addLinks)
                .collect(Collectors.toList()), readCertificate.getLinks());
    }




}
