package br.com.fiap.fiaprestaurant.reservation.application.inputs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The reservation date time cannot be null.")
    private LocalDateTime reservationDateTime;
    @Min(value = 1, message = "The guests must be at least 1.")
    private int guests;
    @NotNull(message = "The restaurant date time cannot be null.")
    private long restaurantId;
    @NotNull(message = "The restaurant date time cannot be null.")
    private long customerId;

}
