package br.com.fiap.fiaprestaurant.reservation.application.gateways;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

public interface ReservationGateway {

    Reservation create(Reservation reservation);

    Reservation findById(long id);

}
