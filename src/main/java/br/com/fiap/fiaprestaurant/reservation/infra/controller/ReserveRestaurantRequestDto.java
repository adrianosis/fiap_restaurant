package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import lombok.*;

import java.time.LocalDateTime;

@Data
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
