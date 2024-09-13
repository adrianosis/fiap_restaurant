package br.com.fiap.fiaprestaurant.reservation.infra.gateways;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationRepositoryGateway implements ReservationGateway {

    @Override
    public Reservation create(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation findById(long id) {
        return null;
    }

    @Override
    public int countByCurrentDateTimeAndRestaurantId(LocalDateTime currentDateTime, long restaurantId) {
        return 0;
    }

    @Override
    public List<Reservation> findAllFinishedReservationsByCustomerId(long customerId) {
        return null;
    }
}
