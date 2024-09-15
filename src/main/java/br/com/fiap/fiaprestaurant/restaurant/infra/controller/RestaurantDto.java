package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private long id;
    private String name;
    private String kitchenType;
    private int capacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String postalCode;

    public static RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getKitchenType(),
                restaurant.getCapacity(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                restaurant.getAddress().getStreet(),
                restaurant.getAddress().getNumber(),
                restaurant.getAddress().getComplement(),
                restaurant.getAddress().getDistrict(),
                restaurant.getAddress().getCity(),
                restaurant.getAddress().getState(),
                restaurant.getAddress().getPostalCode()
        );
    }

    public static List<RestaurantDto> toListDto(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantDto::toDto).toList();
    }

}
