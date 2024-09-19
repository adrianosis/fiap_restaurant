package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllReviewsByCustomerIdUseCase {

    private final ReviewGateway reviewGateway;

    public List<Review> execute(Long customerId) throws Exception {
        return reviewGateway.findAllReviewsByCustomerId(customerId);
    }
}
