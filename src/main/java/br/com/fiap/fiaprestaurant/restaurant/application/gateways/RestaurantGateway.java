package br.com.fiap.fiaprestaurant.restaurant.application.gateways;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;

public interface RestaurantGateway {

    Restaurant create(Restaurant restaurant);

}
