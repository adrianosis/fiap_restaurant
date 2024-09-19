package br.com.fiap.fiaprestaurant.reservation.infra.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservationEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class ReservationRepositoryIT {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void shouldCreateTable() {
        long registers = reservationRepository.count();
        assertThat(registers).isPositive();
    }

    @Test
    void shouldCreateReservation() {
        // Arrange
        var reservation = createReservationEntity();

        // Act
        var savedReservation = reservationRepository.save(reservation);

        // Assert
        assertThat(savedReservation).isInstanceOf(ReservationEntity.class).isNotNull();
        assertThat(savedReservation.getId()).isEqualTo(reservation.getId());
        assertThat(savedReservation.getReservationDateTime()).isEqualTo(reservation.getReservationDateTime());
        assertThat(savedReservation.getGuests()).isEqualTo(reservation.getGuests());
        assertThat(savedReservation.getStatus()).isEqualTo(reservation.getStatus());
        assertThat(savedReservation.getRestaurant()).isEqualTo(reservation.getRestaurant());
        assertThat(savedReservation.getCustomer()).isEqualTo(reservation.getCustomer());
    }

    @Test
    void shouldCountReservationByRestaurantIdAndReservationDateTime() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of (2024,9,15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of (2024,9,15, 20, 0);

        // Act
        var countGuests = reservationRepository.countByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime);

        // Assert
        assertThat(countGuests.orElse(0)).isEqualTo(10);
    }

    @Test
    void shouldFindAllOpenedReservationsByRestaurantIdAndReservationDateTime() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of (2024,9,15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of (2024,9,15, 20, 0);

        // Act
        var reservations = reservationRepository.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
                restaurantId, startDateTime, endDateTime
        );

        //Assert
        assertThat(reservations).hasSize(2);
    }

    @Test
    void shouldFindAllCompletedReservationsByCustomerId() {
        // Arrange
        var customerId = 1L;

        // Act
        var reservations = reservationRepository.findAllCompletedReservationsByCustomerId(customerId);

        //Assert
        assertThat(reservations).hasSize(1);
    }

}