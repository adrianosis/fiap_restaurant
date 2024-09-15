package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public Restaurant execute(long restaurantId) throws Exception {
        return restaurantGateway.findById(restaurantId);
    }

}
