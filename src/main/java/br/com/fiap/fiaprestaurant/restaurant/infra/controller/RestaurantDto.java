package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
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

    public RestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.kitchenType = restaurant.getKitchenType();
        this.capacity = restaurant.getCapacity();
        this.openingTime = restaurant.getOpeningTime();
        this.closingTime = restaurant.getClosingTime();
        this.street = restaurant.getAddress().getStreet();
        this.number = restaurant.getAddress().getNumber();
        this.complement = restaurant.getAddress().getComplement();
        this.district = restaurant.getAddress().getDistrict();
        this.city = restaurant.getAddress().getCity();
        this.state = restaurant.getAddress().getState();
        this.postalCode = restaurant.getAddress().getPostalCode();
    }

    public Restaurant toDomain() {
        return new Restaurant(id, name, kitchenType, capacity,openingTime, closingTime,
                new Address(street, number, complement, district, city, state, postalCode)
        );
    }

}
