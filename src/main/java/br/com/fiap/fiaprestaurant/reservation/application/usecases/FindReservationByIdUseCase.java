package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindReservationByIdUseCase {

    private final ReservationGateway reservationGateway;

    public Reservation execute(long reservationId) {
        return reservationGateway.findById(reservationId);
    }

}
