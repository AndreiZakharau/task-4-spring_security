package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long> {

    Page<Certificate> findAllByCertificateNameLikeAndDescriptionLikeAndPriceBetween(
            String name, String description, double minPrise, double maxPrise, Pageable pageable);
}
