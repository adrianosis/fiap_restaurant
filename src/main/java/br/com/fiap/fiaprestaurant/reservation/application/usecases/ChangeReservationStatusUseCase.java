package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangeReservationStatusUseCase {

    private final ReservationGateway reservationGateway;

    public Reservation execute(long reservationId, ReservationStatus status, String tableTag) throws Exception {
        Reservation reservation = reservationGateway.findById(reservationId);

        reservation.changeStatus(status, tableTag);

        return reservationGateway.save(reservation);
    }

}
