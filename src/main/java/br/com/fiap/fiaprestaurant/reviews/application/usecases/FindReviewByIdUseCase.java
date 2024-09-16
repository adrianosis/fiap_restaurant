package br.com.fiap.fiaprestaurant.reviews.application.usecases;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.reviews.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindReviewByIdUseCase {

    private final ReviewGateway reviewGateway;

    public Review execute(Long reviewId) throws Exception {
        return reviewGateway.findReviewById(reviewId);
    }
}