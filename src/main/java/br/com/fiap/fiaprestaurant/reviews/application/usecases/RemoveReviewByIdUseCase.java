package br.com.fiap.fiaprestaurant.reviews.application.usecases;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveReviewByIdUseCase {

    private final ReviewGateway reviewGateway;

    public void execute(Long reviewId) throws Exception {
        reviewGateway.removeReviewById(reviewId);
    }

}