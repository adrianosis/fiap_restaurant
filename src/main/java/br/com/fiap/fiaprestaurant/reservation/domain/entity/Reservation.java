package br.com.fiap.fiaprestaurant.reservation.domain.entity;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
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

    public void confirmReserve(int reservationCount) throws Exception {
        if ((reservationCount + this.guests) > restaurant.getCapacity()) {
            throw new Exception("Exceeded capacity");
        }

        status = ReservationStatus.RESERVED;
    }

    public void changeStatus(ReservationStatus status, String tableTag) throws Exception {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new Exception("You cannot change a cancelled reservation");
        }
        if (this.status == ReservationStatus.COMPLETED) {
            throw new Exception("You cannot change a completed reservation");
        }

        switch (status) {
            case IN_PROGRESS -> {
                startService = LocalDateTime.now();
                this.tableTag = tableTag;
            }
            case COMPLETED -> {
                if (this.status != ReservationStatus.IN_PROGRESS) {
                    throw new Exception("Only reservation in progress can be changed to completed");
                }
                endService = LocalDateTime.now();
            }
        }
        this.status = status;
    }

}
