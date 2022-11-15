package com.epam.esm.link.linkImpl;


import com.epam.esm.controller.UserController;
import com.epam.esm.dto.orderDto.ReadOrder;
import com.epam.esm.link.AddAbstractLink;
import com.epam.esm.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddOrderLink extends AddAbstractLink<ReadOrder> {

    @Override
    public void addLinks(ReadOrder readOrder) {
        long id = readOrder.getId();
        addIdLink(UserController.class,readOrder,id,SELF_LINK);
        readOrder.add(linkTo(UserController.class)
                .slash(readOrder.getUser().getId())
                .slash("users")
                .withRel("users"));
    }

    @Override
    public void pageLink(Page listPage, ReadOrder readOrder) {
        int totalRecords = listPage.getTotalPages();
        int pages = Pagination.findPages(totalRecords, listPage.getSize());
        int lastPage = Pagination.findLastPage(pages,listPage.getSize() , totalRecords);
        readOrder.add(linkTo(methodOn(UserController.class).getOrders(Pagination.findPrevPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("prev"));
        readOrder.add(linkTo(methodOn(UserController.class).getOrders(Pagination.findNextPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("next"));
    }
}
