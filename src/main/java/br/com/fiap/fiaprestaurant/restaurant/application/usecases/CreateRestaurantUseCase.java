package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public Restaurant execute(Restaurant restaurant) {
        return restaurantGateway.create(restaurant);
    }

}
