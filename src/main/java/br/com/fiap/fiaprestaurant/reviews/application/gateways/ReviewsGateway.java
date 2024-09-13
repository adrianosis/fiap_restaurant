package br.com.fiap.fiaprestaurant.reviews.application.gateways;

import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;

public interface ReviewsGateway {

    Reviews create(Reviews reviews);
}
