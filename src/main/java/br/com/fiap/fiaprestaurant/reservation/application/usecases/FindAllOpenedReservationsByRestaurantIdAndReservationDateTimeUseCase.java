package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase {

    private final ReservationGateway reservationGateway;

    public List<Reservation> execute(long restaurantId, LocalTime startDateTime, LocalTime endDateTime){
        return reservationGateway.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime);
    }

}
