package br.com.fiap.fiaprestaurant.restaurant.application.gateways;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantGateway {

    Restaurant create(Restaurant restaurant);

    Restaurant findById(long restaurantId);

    List<Restaurant> findAllByNameOrLocationOrType(String name, String location, String type);

}
