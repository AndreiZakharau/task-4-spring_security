package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    static final String GET_POPULAR_TAG ="select tags.id,tags.tag_name from tags " +
            "join (select t.tag_name as tag, count(t.tag_name) as count_tag from tags as t " +
            "join certificates_tag as c_t on c_t.tag_id = t.id " +
            "join gift_certificate as c on c.id = c_t.certificate_id " +
            "join orders_certificates as o_c on o_c.certificate_id = c.id " +
            "join orders as o on o.id = o_c.order_id join users as u on u.id = o.user_id " +
            "join (select man.id as u_id, man.nick_name, max(users_sum.sum_orders) from users as man " +
            "join orders as checks on checks.user_id = man.id " +
            "join (select us.id, sum(ord.cost) as sum_orders from orders as ord " +
            "join users as us on us.id = ord.user_id group by ord.user_id) as users_sum " +
            "where users_sum.id = man.id) as resault on resault.u_id = o.user_id " +
            "group by t.tag_name) as count_tags on count_tags.tag = tags.tag_name " +
            "having max(count_tags.count_tag) ";

    /**
     * @param tagName the tagName
     * @return tag by name
     */
    Optional<Tag> findByTagName(String tagName);

    /**
     * @return the most widely used tag of a user with the highest cost of all orders
     */
    @Query( value = GET_POPULAR_TAG, nativeQuery = true)
    Tag getPopularTagWithUser();
}
