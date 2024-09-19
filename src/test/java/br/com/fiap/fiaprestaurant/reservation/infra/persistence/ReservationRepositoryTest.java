package br.com.fiap.fiaprestaurant.reservation.infra.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservationEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationRepositoryTest {

    @Mock
    private ReservationRepository reservationRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateReservation() {
        // Arrange
        var reservation = createReservationEntity();

        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservation);

        // Act
        var savedReservation = reservationRepository.save(reservation);

        // Assert
        assertThat(savedReservation).isNotNull().isEqualTo(reservation);
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
    }

    @Test
    void shouldCountReservationByRestaurantIdAndReservationDateTime() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusHours(1);

        int countGuests = 50;
        when(reservationRepository.countByRestaurantIdAndReservationDateTime(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Optional.of(countGuests));

        // Act
        var foundCountGuests = reservationRepository.countByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime);

        // Assert
        verify(reservationRepository, times(1)).countByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)
        );
        assertThat(foundCountGuests.orElse(0)).isEqualTo(countGuests);
    }

    @Test
    void shouldFindAllOpenedReservationsByRestaurantIdAndReservationDateTime() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusHours(1);

        var reservation1 = createReservationEntity();
        var reservation2 = createReservationEntity();
        var reservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(reservations);

        // Act
        var foundReservations = reservationRepository.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(restaurantId, startDateTime, endDateTime);

        // Assert
        verify(reservationRepository, times(1)).findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)
        );
        assertThat(foundReservations).hasSize(2).containsExactlyInAnyOrder(reservation1, reservation2);
    }

    @Test
    void shouldFindAllCompletedReservationsByCustomerId() {
        // Arrange
        long customerId = 1L;

        var reservation1 = createReservationEntity();
        var reservation2 = createReservationEntity();
        var reservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findAllCompletedReservationsByCustomerId(any(Long.class))).thenReturn(reservations);

        // Act
        var foundReservations = reservationRepository.findAllCompletedReservationsByCustomerId(customerId);

        // Assert
        verify(reservationRepository, times(1)).findAllCompletedReservationsByCustomerId(any(Long.class));
        assertThat(foundReservations).hasSize(2).containsExactlyInAnyOrder(reservation1, reservation2);
    }

}