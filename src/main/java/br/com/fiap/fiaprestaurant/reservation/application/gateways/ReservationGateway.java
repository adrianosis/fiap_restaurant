package br.com.fiap.fiaprestaurant.reservation.application.gateways;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationGateway {

    Reservation create(Reservation reservation);

    Reservation findById(long reservationId);

    int countByRestaurantIdAndReservationDateTime(long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Reservation> findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
            long restaurantId, LocalTime startDateTime, LocalTime endDateTime);

    List<Reservation> findAllFinishedReservationsByCustomerId(long customerId);

}
