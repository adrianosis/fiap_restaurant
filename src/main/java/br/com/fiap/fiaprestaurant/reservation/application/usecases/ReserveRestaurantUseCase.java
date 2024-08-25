package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

public class ReserveRestaurantUseCase {

    private final ReservationGateway reservationGateway;

    public ReserveRestaurantUseCase(ReservationGateway reservationGateway) {
        this.reservationGateway = reservationGateway;
    }

    public Reservation reserve(Reservation reservation) {
        reservation.confirmReserve();

        reservationGateway.create(reservation);

        return reservation;
    }

}
