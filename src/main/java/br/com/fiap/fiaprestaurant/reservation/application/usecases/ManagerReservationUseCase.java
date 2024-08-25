package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;

public class ManagerReservationUseCase {

    private final ReservationGateway reservationGateway;

    public ManagerReservationUseCase(ReservationGateway reservationGateway) {
        this.reservationGateway = reservationGateway;
    }

    public Reservation changeStatus(long reservationId, ReservationStatus status, String tableTag) {
        Reservation reservation = reservationGateway.findById(reservationId);
        reservation.changeStatus(status, tableTag);

        return reservation;
    }

}
