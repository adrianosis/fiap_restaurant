package br.com.fiap.fiaprestaurant.reservation.domain.entity;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;

import java.time.LocalDateTime;

public class Reservation {

    private long id;
    private LocalDateTime reservationDateTime;
    private int guests;
    private LocalDateTime startService;
    private LocalDateTime endService;
    private String tableTag;
    private ReservationStatus status;
    private Restaurant restaurant;
    private Customer customer;

    public Reservation(LocalDateTime reservationDateTime, int guests, Restaurant restaurant, Customer customer) {
        this.reservationDateTime = reservationDateTime;
        this.guests = guests;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public Reservation(long id, LocalDateTime reservationDateTime, int guests, LocalDateTime startService,
                       LocalDateTime endService, String tableTag, ReservationStatus status, Restaurant restaurant, Customer customer) {
        this.id = id;
        this.reservationDateTime = reservationDateTime;
        this.guests = guests;
        this.startService = startService;
        this.endService = endService;
        this.tableTag = tableTag;
        this.status = status;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public void confirmReserve() {
        status = ReservationStatus.RESERVED;
    }

    public void changeStatus(ReservationStatus status, String tableTag) {
        this.status = status;

        switch (status) {
            case IN_PROGRESS -> {
                startService = LocalDateTime.now();
                this.tableTag = tableTag;
            }
            case FINISHED -> endService = LocalDateTime.now();
            default -> {}
        }
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public int getGuests() {
        return guests;
    }

    public LocalDateTime getStartService() {
        return startService;
    }

    public LocalDateTime getEndService() {
        return endService;
    }

    public String getTableTag() {
        return tableTag;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }
}
