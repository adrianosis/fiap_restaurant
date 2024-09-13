package br.com.fiap.fiaprestaurant.reviews.application.usecases;


import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewsGateway;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;

public class CreateReviewsUseCase {

    private final ReviewsGateway reviewsGateway;

    public CreateReviewsUseCase(ReviewsGateway reviewsGateway) {
        this.reviewsGateway = reviewsGateway;
    }

    public Reviews create(Reviews reviews) {
        return reviewsGateway.create(reviews);
    }
}
