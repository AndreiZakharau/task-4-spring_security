package com.epam.esm.controller;


import com.epam.esm.dto.certificateDto.CertificateDto;
import com.epam.esm.dto.certificateDto.CreateCertificate;
import com.epam.esm.dto.certificateDto.ReadCertificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1.1/certificates")
public class CertificateController {

    private final CertificateServiceImpl service;
//    private final AddCertificateLink certificateLink;

    /**
     * created certificate
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     *
     * @param certificate the CreateCertificate (certificate Dto)
     * @return new createCertificate (certificate Dto)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCertificate addCertificate(@RequestBody CreateCertificate certificate) {
        service.saveEntity(certificate);
//        certificateLink.addCreateLink(certificate);
        return certificate;
    }


    /**
     * @param page the page
     * @param size the size
     * @return list ReadCertificate (certificate Dto)
     */
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<ReadCertificate> listAllCertificates(@RequestParam(value = "page",defaultValue = "1", required = false) Integer page,
//                                                                @RequestParam(value = "size",defaultValue = "10", required = false) Integer size) {
//        int offset = Pagination.offset(page, size);
//        ReadCertificate readCertificate = new ReadCertificate();
//        List<ReadCertificate> list = service.getAllEntity(size, offset);
//        certificateLink.pageLink(page, size, readCertificate);
//        return CollectionModel.of(list.stream()
//                .peek(certificateLink::addLinks)
//                .collect(Collectors.toList()), readCertificate.getLinks());
//    }

    /**
     * @param name the name
     * @return certificate by name or part of name
     */
//    @GetMapping("/{name}/name")
//    @ResponseStatus(HttpStatus.OK)
//    public List<ReadCertificate> getCertificateByName(@PathVariable String name) {
//        return service.getCertificateByName(name);
//    }

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
//        certificateLink.addLinks(model.get());
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
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@RequestBody CertificateDto certificate, @PathVariable long id) {
        service.updateEntity(id, certificate);
//        certificateLink.addLinks(certificate);
        return certificate;
    }

    /**
     * delete certificate by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        service.deleteEntity(id);

    }


    /**
     * @param name the certificate name
     * @param tagNames the certificate tagNames
     * @param description the certificate description
     * @param price the certificate price
     * @param sortColumns the certificate column
     * @param orderTypes the type (asc or desc)
     * @param page the page
     * @param size the size
     * @return list certificates
     */
//    @GetMapping("/sort")
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<ReadCertificate> getCertificateByParameters(
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "tagName", required = false) List<String> tagNames,
//            @RequestParam(value = "description", required = false) String description,
//            @RequestParam(value = "price", required = false) List<Double> price,
//            @RequestParam(value = "sort", required = false) List<String> sortColumns,
//            @RequestParam(value = "order", required = false) List<String> orderTypes,
//            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
//            @RequestParam(value = "size", defaultValue = "10", required = false) int size
//    ) {
//        int offset = Pagination.offset(page, size);
//        ReadCertificate readCertificate =new ReadCertificate();
//        List<ReadCertificate> list = service.getCertificateByParameters(name, tagNames, description, price,sortColumns,orderTypes, offset,size );
//        certificateLink.pageLink(page,size,readCertificate);
//        return CollectionModel.of(list.stream()
//                .peek(certificateLink::addLinks)
//                .collect(Collectors.toList()), readCertificate.getLinks());
//    }

    /**
     * @param tagNames the list tagName
     * @return list ReadCertificate (certificate Dto)
     */
//    @GetMapping("/tags")
//    @ResponseStatus(HttpStatus.OK)
//    public List<ReadCertificate> getCertificateByTag(@RequestParam(value = "tagName", required = false) List<String> tagNames) {
//        List<ReadCertificate> list = service.getCertificatesByTags(tagNames);
//        return list.stream()
//                .peek(certificateLink::addLinks)
//                .collect(Collectors.toList());
//    }


}
