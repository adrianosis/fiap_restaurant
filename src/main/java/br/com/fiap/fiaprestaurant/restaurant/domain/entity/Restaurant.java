package br.com.fiap.fiaprestaurant.restaurant.domain.entity;

import java.time.LocalTime;

public class Restaurant {

    private long id;
    private String name;
    private String kitchenType;
    private int capacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Address address;

    public Restaurant(long id, String name, String kitchenType, int capacity, LocalTime openingTime, LocalTime closingTime, Address address) {
        this.id = id;
        this.name = name;
        this.kitchenType = kitchenType;
        this.capacity = capacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public Address getAddress() {
        return address;
    }
}
