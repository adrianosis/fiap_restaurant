package br.com.fiap.fiaprestaurant.review.application.gateways;

import br.com.fiap.fiaprestaurant.review.domain.entity.Review;

import java.util.List;

public interface ReviewGateway {
    Review create(Review review);
    List<Review> findAllReviewsByRestaurantId(Long restaurantId);
    List<Review> findAllReviewsByCustomerId(Long customerId);
    Review findReviewById(Long reviewId);
    void removeReviewById(Long reviewId);
}
