package br.com.fiap.fiaprestaurant.reviews.infra.persistance;

import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select r from ReviewEntity r " +
            "where r.reservation.restaurant.id = :restaurantId ")
    List<ReviewEntity> findAllByRestaurantId(Long restaurantId);
}
