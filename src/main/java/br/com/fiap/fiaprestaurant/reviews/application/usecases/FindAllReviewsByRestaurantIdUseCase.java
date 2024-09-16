package br.com.fiap.fiaprestaurant.reviews.application.usecases;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllReviewsByRestaurantIdUseCase {

    private final ReviewGateway reviewGateway;

    public List<Review> execute(Long restaurantId) throws Exception {
        return reviewGateway.findAllReviewsByRestaurantId(restaurantId);
    }
}
