package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservationEntity;
import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.saveReservation;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindReservationByIdUseCaseIT {

    @Autowired
    private ReservationGateway reservationGateway;

    @Autowired
    private FindReservationByIdUseCase findReservationByIdUseCase;

    @Test
    void shouldFindReservationById() {
        // Arrange
        long reservationId = 5L;
        var reservation = saveReservation(reservationGateway);

        // Act
        var savedReservation = findReservationByIdUseCase.execute(reservationId);

        // Assert
        assertThat(savedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(savedReservation.getId()).isGreaterThan(0);
        assertThat(savedReservation.getReservationDateTime()).isEqualTo(reservation.getReservationDateTime());
        assertThat(savedReservation.getGuests()).isEqualTo(reservation.getGuests());
        assertThat(savedReservation.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(savedReservation.getRestaurant().getId()).isEqualTo(reservation.getRestaurant().getId());
        assertThat(savedReservation.getCustomer().getId()).isEqualTo(reservation.getCustomer().getId());
    }

}