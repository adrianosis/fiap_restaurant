package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "The name cannot be blank.")
    @Size(max = 30, message = "The name must have at most 30 characters.")
    private String name;
    @NotBlank(message = "The kitchen type cannot be blank.")
    @Size(max = 30, message = "The kitchen type must have at most 30 characters.")
    private String kitchenType;
    @Min(value = 1, message = "The capacity must be at least 1.")
    private int capacity;
    @NotNull(message = "The opening time cannot be null.")
    private LocalTime openingTime;
    @NotNull(message = "The opening time cannot be null.")
    private LocalTime closingTime;
    @NotBlank(message = "The street cannot be blank.")
    @Size(max = 60, message = "The street must have at most 60 characters.")
    private String street;
    @NotBlank(message = "The number cannot be blank.")
    @Size(max = 10, message = "The number must have at most 10 characters.")
    private String number;
    @Size(max = 60, message = "The complement must have at most 60 characters.")
    private String complement;
    @NotBlank(message = "The district cannot be blank.")
    @Size(max = 30, message = "The district must have at most 30 characters.")
    private String district;
    @NotBlank(message = "The city cannot be blank.")
    @Size(max = 30, message = "The city must have at most 30 characters.")
    private String city;
    @NotBlank(message = "The state cannot be blank.")
    @Size(max = 2, message = "The state must have at most 2 characters.")
    private String state;
    @NotBlank(message = "The postal code cannot be blank.")
    @Size(max = 8, message = "The postal code must have at most 8 characters.")
    private String postalCode;

    public Restaurant toDomain() {
        return new Restaurant(0, name, kitchenType, capacity,openingTime, closingTime,
                new Address(street, number, complement, district, city, state, postalCode)
        );
    }

}
