package br.com.fiap.fiaprestaurant.reservation.application.inputs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRestaurantInput {

    private LocalDateTime reservationDateTime;
    private int guests;
    private long restaurantId;
    private long customerId;

}
