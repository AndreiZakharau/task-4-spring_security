package com.epam.esm.link;

import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AddAbstractLink <T extends RepresentationModel<T>> implements AddLink<T> {

    protected final String SELF_LINK = "self";
    protected final String UPDATE_LINK = "update";
    protected final String DELETE_LINK = "delete";
    protected final String PREV_LINK = "prev";
    protected final String NEXT_LINK = "next";

    protected void addIdLink(Class<?> controllerClass, T dto, long id, String linkName) {
        dto.add(linkTo(controllerClass).slash(id).withRel(linkName));
    }

    protected void addIdLinks(Class<?> controllerClass, T dto, long id, String ...linkNames) {
        for (String linkName : linkNames) {
            addIdLink(controllerClass, dto, id, linkName);
        }
    }

    protected void pageLink(Class<?> controllerClass,T dto){

       dto.add(linkTo(methodOn(controllerClass)).withRel(PREV_LINK));
        dto.add(linkTo(methodOn(controllerClass)).withRel(NEXT_LINK));
    }
}


