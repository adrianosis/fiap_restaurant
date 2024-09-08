package br.com.fiap.fiaprestaurant.restaurant.infra.utils;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.controller.SaveRestaurantDto;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.AddressEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;

import java.time.LocalTime;

public class RestaurantHelper {

    public static Restaurant createRestaurant() {
        Address address = Address.builder()
                .street("Av Rebouças")
                .number("1900")
                .district("Itaim")
                .city("São Paulo")
                .state("SP")
                .postalCode("05300100")
                .build();

        return Restaurant.builder()
                .name("PIZZARIA 10")
                .kitchenType("PIZZARIA")
                .capacity(100)
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(22, 0))
                .address(address)
                .build();
    }

    public static RestaurantEntity createRestaurantEntity() {
        AddressEntity address = AddressEntity.builder()
                .street("Av Rebouças")
                .number("1900")
                .district("Itaim")
                .city("São Paulo")
                .state("SP")
                .postalCode("05300100")
                .build();

        return RestaurantEntity.builder()
                .name("PIZZARIA 10")
                .kitchenType("PIZZARIA")
                .capacity(100)
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(22, 0))
                .address(address)
                .build();
    }

    public static RestaurantEntity saveRestaurantEntity(RestaurantRepository restaurantRepository) {
        var restaurant = createRestaurantEntity();
        return restaurantRepository.save(restaurant);
    }

    public static SaveRestaurantDto createRestaurantRequest(){
        return SaveRestaurantDto.builder()
                .name("PIZZARIA 10")
                .kitchenType("PIZZARIA")
                .capacity(100)
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(22, 0))

                .street("Av Rebouças")
                .number("1900")
                .district("Itaim")
                .city("São Paulo")
                .state("SP")
                .postalCode("05300100")
                .build();
    }

}
