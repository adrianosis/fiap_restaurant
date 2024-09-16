package br.com.fiap.fiaprestaurant.reservation.infra.gateways;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class ReservationRepositoryGateway implements ReservationGateway {

    private final ReservationRepository repository;
    private final ReservationEntityMapper mapper;

    @Override
    public Reservation create(Reservation reservation) {
        ReservationEntity entity = mapper.toEntity(reservation);
        repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Reservation findById(long reservationId) {
        return repository.findById(reservationId)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }

    @Override
    public int countByRestaurantIdAndReservationDateTime(long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.countByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime);
    }

    @Override
    public List<Reservation> findAllOpenedReservationsByRestaurantIdAndReservationDateTime(long restaurantId,
                                                                                           LocalDateTime startDateTime,
                                                                                           LocalDateTime endDateTime) {
        return repository.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Reservation> findAllFinishedReservationsByCustomerId(long customerId) {
        return repository.findAllFinishedReservationsByCustomerId(customerId).stream().map(mapper::toDomain).toList();
    }

}
