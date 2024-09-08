package br.com.fiap.fiaprestaurant.restaurant.domain.entity;


import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private long id;
    private String name;
    private String kitchenType;
    private int capacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Address address;

}
