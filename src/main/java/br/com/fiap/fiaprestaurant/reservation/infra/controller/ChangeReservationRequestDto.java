package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeReservationRequestDto {

    private ReservationStatus status;
    private String tableTag;

}
