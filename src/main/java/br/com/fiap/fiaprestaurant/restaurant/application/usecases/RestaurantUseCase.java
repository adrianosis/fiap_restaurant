package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;

public class RestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public RestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant create(Restaurant restaurant) {
        return restaurantGateway.create(restaurant);
    }

}
