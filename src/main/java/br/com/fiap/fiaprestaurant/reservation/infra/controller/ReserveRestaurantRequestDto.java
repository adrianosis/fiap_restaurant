package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRestaurantRequestDto {

    private LocalDateTime reservationDateTime;
    private int guests;
    private long restaurantId;
    private long customerId;

    public ReserveRestaurantInput toInput() {
        return new ReserveRestaurantInput(reservationDateTime, guests, restaurantId, customerId);
    }

}
