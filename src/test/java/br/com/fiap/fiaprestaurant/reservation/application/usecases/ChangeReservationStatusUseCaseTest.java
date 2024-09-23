package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChangeReservationStatusUseCaseTest {

    private ChangeReservationStatusUseCase changeReservationStatusUseCase;

    @Mock
    private ReservationGateway reservationGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        changeReservationStatusUseCase = new ChangeReservationStatusUseCase( reservationGateway);
    }

    @Test
    void shouldChangeReservationToInProgress() throws Exception {
        // Arrange
        long id = 1L;
        var reservation = createReservation();
        reservation.setId(id);
        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);
        when(reservationGateway.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        var changedReservation = changeReservationStatusUseCase.execute(id, ReservationStatus.IN_PROGRESS, "A07");

        // Assert
        verify(reservationGateway, times(1)).findById(id);
        verify(reservationGateway, times(1)).save(reservation);
        assertThat(changedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(changedReservation.getId()).isEqualTo(reservation.getId());
        assertThat(changedReservation.getStatus()).isEqualTo(ReservationStatus.IN_PROGRESS);
        assertThat(changedReservation.getStartService()).isEqualTo(reservation.getStartService());
        assertThat(changedReservation.getTableTag()).isEqualTo(reservation.getTableTag());
    }

    @Test
    void shouldChangeReservationToCompleted() throws Exception {
        // Arrange
        long id = 1L;
        var reservation = createReservation();
        reservation.setId(id);
        reservation.setStatus(ReservationStatus.IN_PROGRESS);
        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);
        when(reservationGateway.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        var changedReservation = changeReservationStatusUseCase.execute(id, ReservationStatus.COMPLETED, "A07");

        // Assert
        verify(reservationGateway, times(1)).findById(id);
        verify(reservationGateway, times(1)).save(reservation);
        assertThat(changedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(changedReservation.getId()).isEqualTo(reservation.getId());
        assertThat(changedReservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
        assertThat(changedReservation.getStartService()).isEqualTo(reservation.getStartService());
        assertThat(changedReservation.getEndService()).isNotNull();
        assertThat(changedReservation.getTableTag()).isEqualTo(reservation.getTableTag());
    }

    @Test
    public void shouldThrowException_WhenChangeReservation_FromStatusReservedToCompleted() {
        // Arrange
        long id = 1L;
        var reservation = createReservation();
        reservation.setId(id);
        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);

        // Act
        // Assert
        assertThatThrownBy(
                () -> changeReservationStatusUseCase.execute(id, ReservationStatus.COMPLETED, "A07"))
                .isInstanceOf(RestaurantException.class)
                .hasMessage("Only reservation in progress can be changed to completed");
    }

    @Test
    public void shouldThrowException_WhenChangeReservation_WithStatusCanceled() {
        // Arrange
        long id = 1L;
        var reservation = createReservation();
        reservation.setId(id);
        reservation.setStatus(ReservationStatus.CANCELLED);
        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);

        // Act
        // Assert
        assertThatThrownBy(
                () -> changeReservationStatusUseCase.execute(id, ReservationStatus.IN_PROGRESS, "A07"))
                .isInstanceOf(RestaurantException.class)
                .hasMessage("You cannot change a cancelled reservation");
    }

    @Test
    public void shouldThrowException_WhenChangeReservation_WithStatusCompleted() {
        // Arrange
        long id = 1L;
        var reservation = createReservation();
        reservation.setId(id);
        reservation.setStatus(ReservationStatus.COMPLETED);
        when(reservationGateway.findById(any(Long.class))).thenReturn(reservation);

        // Act
        // Assert
        assertThatThrownBy(
                () -> changeReservationStatusUseCase.execute(id, ReservationStatus.COMPLETED, "A07"))
                .isInstanceOf(RestaurantException.class)
                .hasMessage("You cannot change a completed reservation");
    }

}