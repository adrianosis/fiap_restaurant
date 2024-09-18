package br.com.fiap.fiaprestaurant.reservation.application.gateways;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationGateway {

    Reservation save(Reservation reservation);

    Reservation findById(long reservationId);

    int countByRestaurantIdAndReservationDateTime(long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Reservation> findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
            long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Reservation> findAllCompletedReservationsByCustomerId(long customerId);

}
