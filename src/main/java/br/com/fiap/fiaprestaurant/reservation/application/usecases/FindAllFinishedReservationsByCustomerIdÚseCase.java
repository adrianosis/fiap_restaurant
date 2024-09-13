package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllFinishedReservationsByCustomerIdÚseCase {

    private final ReservationGateway reservationGateway;

    public List<Reservation> execute(long customerId){
        return reservationGateway.findAllFinishedReservationsByCustomerId(customerId);
    }

}
