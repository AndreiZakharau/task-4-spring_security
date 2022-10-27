package com.epam.esm.link;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

public interface AddLink <T extends RepresentationModel<T>>{

    void addLinks(T t);

    void pageLink(Page list, T t);

}
