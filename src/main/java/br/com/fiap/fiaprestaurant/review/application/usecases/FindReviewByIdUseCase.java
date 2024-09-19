package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindReviewByIdUseCase {

    private final ReviewGateway reviewGateway;

    public Review execute(Long reviewId) throws Exception {
        return reviewGateway.findReviewById(reviewId);
    }
}