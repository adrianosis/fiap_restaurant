package br.com.fiap.fiaprestaurant.reservation.application.gateways;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

import java.time.LocalDateTime;

public interface ReservationGateway {

    Reservation create(Reservation reservation);

    Reservation findById(long id);

    int countByCurrentDateTimeAndRestaurantId(LocalDateTime currentDateTime, long restaurantId);

}
