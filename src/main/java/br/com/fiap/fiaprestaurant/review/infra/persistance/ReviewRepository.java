package br.com.fiap.fiaprestaurant.review.infra.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select r from ReviewEntity r " +
            "join r.reservation re  " +
            "where re.restaurant.id = :restaurantId ")
    List<ReviewEntity> findAllByRestaurantId(Long restaurantId);

    @Query("select r from ReviewEntity r " +
            "join r.reservation re  " +
            "where re.customer.id = :customerId ")
    List<ReviewEntity> findAllByCustomerId(Long customerId);
}
