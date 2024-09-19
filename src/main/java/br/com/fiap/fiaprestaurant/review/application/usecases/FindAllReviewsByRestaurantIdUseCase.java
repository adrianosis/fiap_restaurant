package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllReviewsByRestaurantIdUseCase {

    private final ReviewGateway reviewGateway;

    public List<Review> execute(Long restaurantId) throws RestaurantException {
        return reviewGateway.findAllReviewsByRestaurantId(restaurantId);
    }
}
