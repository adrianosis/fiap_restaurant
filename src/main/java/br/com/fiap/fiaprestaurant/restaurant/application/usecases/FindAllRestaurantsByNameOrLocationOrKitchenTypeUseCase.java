package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase {


    private final RestaurantGateway restaurantGateway;

    public List<Restaurant> execute(String name, String location, String type) {
        return restaurantGateway.findAllByNameOrLocationOrType(name, location, type);
    }

}
