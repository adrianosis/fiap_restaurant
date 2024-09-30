package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FindReservationByIdUseCaseTest {

    private FindReservationByIdUseCase findReservationByIdUseCase;

    @Mock
    private ReservationGateway reservationGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findReservationByIdUseCase = new FindReservationByIdUseCase(reservationGateway);
    }

    @Test
    void shouldFindReservationByIdUseCase() {
        // Arrange
        long reservationId = 1L;

        var reservation = createReservation();

        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);

        // Act
        var foundReservation = findReservationByIdUseCase.execute(reservationId);

        // Assert
        assertThat(foundReservation).isNotNull().isInstanceOf(Reservation.class);
        assertThat(foundReservation.getId()).isEqualTo(reservation.getId());
        assertThat(foundReservation.getReservationDateTime()).isNotNull().isEqualTo(reservation.getReservationDateTime());
        assertThat(foundReservation.getGuests()).isEqualTo(reservation.getGuests());
        assertThat(foundReservation.getStatus()).isEqualTo(reservation.getStatus());
        assertThat(foundReservation.getRestaurant()).isNotNull().isEqualTo(reservation.getRestaurant());
        assertThat(foundReservation.getCustomer()).isNotNull().isEqualTo(reservation.getCustomer());
    }

}