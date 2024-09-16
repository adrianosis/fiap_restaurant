package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCaseTest {

    private FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;

    @Mock
    private ReservationGateway reservationGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase = new FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase(reservationGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldFindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of (2024,9,15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of (2024,9,15, 20, 0);

        var reservation1 = createReservation();
        var reservation2 = createReservation();
        var reservations = Arrays.asList(reservation1, reservation2);
        when(reservationGateway.findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);

        // Act
        var foundReservations = findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase.execute(
                restaurantId, startDateTime, endDateTime);

        // Assert
        verify(reservationGateway, times(1)).findAllOpenedReservationsByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class));
        assertThat(foundReservations).hasSize(2).containsExactlyInAnyOrder(reservation1, reservation2);
    }

}