package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReserveRestaurantInput;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
class ReserveRestaurantUseCaseIT {

    @Autowired
    private ReserveRestaurantUseCase reserveRestaurantUseCase;

    @Test
    void shouldReserveRestaurant() throws Exception {
        // Arrange
        var reserveRestaurantInput = createReserveRestaurantInput();

        // Act
        var savedReservation = reserveRestaurantUseCase.execute(reserveRestaurantInput);

        // Assert
        assertThat(savedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(savedReservation.getId()).isGreaterThan(0);
        assertThat(savedReservation.getReservationDateTime()).isEqualTo(reserveRestaurantInput.getReservationDateTime());
        assertThat(savedReservation.getGuests()).isEqualTo(reserveRestaurantInput.getGuests());
        assertThat(savedReservation.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(savedReservation.getRestaurant().getId()).isEqualTo(reserveRestaurantInput.getRestaurantId());
        assertThat(savedReservation.getCustomer().getId()).isEqualTo(reserveRestaurantInput.getCustomerId());
    }

}