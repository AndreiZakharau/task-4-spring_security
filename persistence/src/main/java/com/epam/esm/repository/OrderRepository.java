package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    /**
     * @param userId the user id
     * @return list orders by user id
     */
    List<Order> getOrdersByUserId(long userId);

    /**
     * @param id the user id
     * @param pageable the pageable
     * @return get a list of щквукы with a page
     */
    Page<Order> findOrdersByUserId(long id, Pageable pageable);
}
