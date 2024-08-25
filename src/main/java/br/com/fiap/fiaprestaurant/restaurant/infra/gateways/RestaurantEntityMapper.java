package br.com.fiap.fiaprestaurant.restaurant.infra.gateways;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.AddressEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;

public class RestaurantEntityMapper {

    public RestaurantEntity toEntity(Restaurant restaurant) {
        return new RestaurantEntity(restaurant.getId(), restaurant.getName(),
                restaurant.getKitchenType(), restaurant.getCapacity(),
                restaurant.getOpeningTime(), restaurant.getClosingTime(),
                new AddressEntity(restaurant.getAddress().getStreet(),
                        restaurant.getAddress().getNumber(),
                        restaurant.getAddress().getComplement(),
                        restaurant.getAddress().getDistrict(),
                        restaurant.getAddress().getCity(),
                        restaurant.getAddress().getState(),
                        restaurant.getAddress().getPostalCode())
        );
    }

    public Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(entity.getId(), entity.getName(),
                entity.getKitchenType(), entity.getCapacity(),
                entity.getOpeningTime(), entity.getClosingTime(),
                new Address(entity.getAddress().getStreet(),
                        entity.getAddress().getNumber(),
                        entity.getAddress().getComplement(),
                        entity.getAddress().getDistrict(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getState(),
                        entity.getAddress().getPostalCode())
        );
    }

}
