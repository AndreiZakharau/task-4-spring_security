package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EntityService<T,K,F> {


    Page<T> getAllEntity(int limit, int offset);

    void saveEntity(K k);

    F updateEntity(long id, F f);

    Optional<T> findById(long id);

    void deleteEntity(long id);

    long countAll();
}
