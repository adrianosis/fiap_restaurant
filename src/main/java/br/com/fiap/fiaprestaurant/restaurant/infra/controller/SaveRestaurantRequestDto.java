package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRestaurantRequestDto {

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

    public Restaurant toDomain() {
        return new Restaurant(0, name, kitchenType, capacity,openingTime, closingTime,
                new Address(street, number, complement, district, city, state, postalCode)
        );
    }

}
