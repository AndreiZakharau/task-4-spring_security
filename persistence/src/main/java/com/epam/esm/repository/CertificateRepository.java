package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long>, QueryByExampleExecutor<Certificate> {

    /**
     * @param name the certificateName
     * @param description the description
     * @param minPrise the minPrise
     * @param maxPrise the maxPrise
     * @param tags the tagName
     * @param pageable the pageable
     * @return Page certificates sorting by parameters
     */
    Page<Certificate> findAllDistinctByCertificateNameLikeOrDescriptionLikeOrPriceBetweenOrTagsIn(
            String name, String description,  double minPrise, double maxPrise,List<Tag> tags, Pageable pageable);

}
