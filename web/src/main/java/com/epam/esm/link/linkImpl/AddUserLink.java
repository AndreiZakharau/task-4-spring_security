package com.epam.esm.link.linkImpl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.link.AddAbstractLink;
import com.epam.esm.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddUserLink extends AddAbstractLink<ReadUser> {

    @Override
    public void addLinks(ReadUser readUser) {
        long id = readUser.getId();
        addIdLink(UserController.class, readUser, id, SELF_LINK);
        readUser.add(linkTo(UserController.class)
                .slash(id)
                .slash("orders")
                .withRel("orders"));

    }

    @Override
    public void pageLink(Page listPage, ReadUser readUser) {
        int totalRecords = listPage.getTotalPages();
        int pages = Pagination.findPages(totalRecords, listPage.getSize());
        int lastPage = Pagination.findLastPage(pages,listPage.getSize() , totalRecords);
        readUser.add(linkTo(methodOn(UserController.class).listAllUsers(Pagination.findPrevPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("prev"));
        readUser.add(linkTo(methodOn(UserController.class).listAllUsers(Pagination.findNextPage(listPage.getNumber(), lastPage), listPage.getSize()))
                .withRel("next"));
    }

}
