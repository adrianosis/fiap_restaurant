package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReserveRestaurantInput;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReserveRestaurantUseCaseTest {

    private ReserveRestaurantUseCase reserveRestaurantUseCase;

    @Mock
    private ReservationGateway reservationGateway;
    @Mock
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    @Mock
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        reserveRestaurantUseCase = new ReserveRestaurantUseCase(reservationGateway, findRestaurantByIdUseCase, findCustomerByIdUseCase);
    }

    @Test
    void shouldReserveRestaurant() throws Exception {
        // Arrange
        var customer = createCustomer();
        var restaurant = createRestaurant();
        var reservation = createReservation();
        reservation.setId(4L);
        var reserveRestaurantInput = createReserveRestaurantInput();

        when(reservationGateway.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationGateway.countByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(10);
        when(findCustomerByIdUseCase.execute(any(Long.class))).thenReturn(customer);
        when(findRestaurantByIdUseCase.execute(any(Long.class))).thenReturn(restaurant);

        // Act
        var savedReservation = reserveRestaurantUseCase.execute(reserveRestaurantInput);

        // Assert
        verify(reservationGateway, times(1)).save(any(Reservation.class));
        verify(reservationGateway, times(1)).countByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(findCustomerByIdUseCase, times(1)).execute(any(Long.class));
        verify(findRestaurantByIdUseCase, times(1)).execute(any(Long.class));

        assertThat(savedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(savedReservation.getId()).isGreaterThan(0);
        assertThat(savedReservation.getReservationDateTime()).isEqualTo(reservation.getReservationDateTime());
        assertThat(savedReservation.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(savedReservation.getCustomer()).isEqualTo(customer);
        assertThat(savedReservation.getRestaurant()).isEqualTo(reservation.getRestaurant());
    }

    @Test
    public void shouldThrowException_WhenReserveRestaurant_ExceededCapacity() throws Exception {
        // Arrange
        var customer = createCustomer();
        var restaurant = createRestaurant();
        var reservation = createReservation();

        var reserveRestaurantInput = createReserveRestaurantInput();
        when(reservationGateway.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationGateway.countByRestaurantIdAndReservationDateTime(
                any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(90);
        when(findCustomerByIdUseCase.execute(any(Long.class))).thenReturn(customer);
        when(findRestaurantByIdUseCase.execute(any(Long.class))).thenReturn(restaurant);

        // Act
        // Assert
        assertThatThrownBy(
                () -> reserveRestaurantUseCase.execute(reserveRestaurantInput))
                .isInstanceOf(Exception.class)
                .hasMessage("Exceeded capacity");
    }

}