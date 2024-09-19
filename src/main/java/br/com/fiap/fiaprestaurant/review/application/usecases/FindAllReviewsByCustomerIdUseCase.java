package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllReviewsByCustomerIdUseCase {

    private final ReviewGateway reviewGateway;

    public List<Review> execute(Long customerId) throws RestaurantException {
        return reviewGateway.findAllReviewsByCustomerId(customerId);
    }
}
