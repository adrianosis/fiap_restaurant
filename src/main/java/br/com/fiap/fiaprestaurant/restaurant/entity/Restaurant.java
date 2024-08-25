package br.com.fiap.fiaprestaurant.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalTime;

@Entity
public class Restaurant {

    @Id
    private long id;
    private String name;
    @Column(name = "kitchen_type")
    private String kitchenType;
    private int capacity;
    @Column(name = "opening_time")
    private LocalTime openingTime;
    @Column(name = "closing_time")
    private LocalTime closingTime;
    @Embedded
    private Address address;

}
