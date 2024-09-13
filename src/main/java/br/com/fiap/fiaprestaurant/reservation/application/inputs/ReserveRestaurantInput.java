package br.com.fiap.fiaprestaurant.reservation.application.inputs;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveRestaurantInput {

    private LocalDateTime reservationDateTime;
    private int guests;
    private long restaurantId;
    private long customerId;

}
