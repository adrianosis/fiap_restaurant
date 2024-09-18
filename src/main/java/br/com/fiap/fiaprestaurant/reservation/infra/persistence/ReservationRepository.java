package br.com.fiap.fiaprestaurant.reservation.infra.persistence;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("select sum(r.guests) from ReservationEntity r " +
           "where r.restaurant.id = :restaurantId " +
           "and r.reservationDateTime between :startDateTime and :endDateTime " +
           "and r.status in ('RESERVED', 'WAITING', 'IN_PROGRESS') ")
    Optional<Integer> countByRestaurantIdAndReservationDateTime(long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select r from ReservationEntity r " +
           "where r.restaurant.id = :restaurantId " +
           "and r.reservationDateTime between :startDateTime and :endDateTime " +
           "and r.status in ('RESERVED', 'WAITING', 'IN_PROGRESS') " +
           "order by r.reservationDateTime")
    List<ReservationEntity> findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
            long restaurantId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select r from ReservationEntity r " +
           "where r.customer.id = :customerId " +
           "and r.status = 'COMPLETED' " +
           "order by r.endService")
    List<ReservationEntity> findAllCompletedReservationsByCustomerId(long customerId);

}
