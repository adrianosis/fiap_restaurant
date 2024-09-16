package br.com.fiap.fiaprestaurant.reviews.application.gateways;

import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;

import java.util.List;

public interface ReviewGateway {
    Review create(Review review);
    List<Review> findAllReviewsByRestaurantId(Long restaurantId);
    Review findReviewById(Long reviewId);
    void removeReviewById(Long reviewId);
}
