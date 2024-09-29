package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public Restaurant execute(long restaurantId, Restaurant restaurantInput) {
        Restaurant restaurant = restaurantGateway.findById(restaurantId);
        restaurant.setName(restaurantInput.getName());
        restaurant.setCapacity(restaurantInput.getCapacity());
        restaurant.setKitchenType(restaurantInput.getKitchenType());
        restaurant.setOpeningTime(restaurantInput.getOpeningTime());
        restaurant.setClosingTime(restaurantInput.getClosingTime());
        restaurant.setAddress(restaurantInput.getAddress());

        return restaurantGateway.save(restaurant);
    }

}
