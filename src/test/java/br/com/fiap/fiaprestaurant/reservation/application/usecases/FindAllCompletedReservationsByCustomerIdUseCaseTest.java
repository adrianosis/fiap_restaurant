package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllCompletedReservationsByCustomerIdUseCaseTest {

    private FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase;

    @Mock
    private ReservationGateway reservationGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllCompletedReservationsByCustomerIdUseCase = new FindAllCompletedReservationsByCustomerIdUseCase(reservationGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldFindAllCompletedReservationsByCustomerIdUseCase() {
        // Arrange
        var customerId = 1L;

        var reservation1 = createReservation();
        var reservation2 = createReservation();
        var reservations = Arrays.asList(reservation1, reservation2);
        when(reservationGateway.findAllCompletedReservationsByCustomerId(any(Long.class)))
                .thenReturn(reservations);

        // Act
        var foundReservations = findAllCompletedReservationsByCustomerIdUseCase.execute(customerId);

        // Assert
        verify(reservationGateway, times(1)).findAllCompletedReservationsByCustomerId(customerId);
        assertThat(foundReservations).hasSize(2).containsExactlyInAnyOrder(reservation1, reservation2);
    }

}