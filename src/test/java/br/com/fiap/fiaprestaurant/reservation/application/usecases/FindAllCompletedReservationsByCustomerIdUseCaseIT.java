package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindAllCompletedReservationsByCustomerIdUseCaseIT {

    @Autowired
    private FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase;

    @Test
    void shouldFindAllCompletedReservationsByCustomerIdUseCase() {
        var customerId = 1L;

        // Act
        var reservations = findAllCompletedReservationsByCustomerIdUseCase.execute(customerId);

        // Assert
        assertThat(reservations).hasSize(1).allSatisfy(reservation -> {
            assertThat(reservation).isNotNull();
            assertThat(reservation).isInstanceOf(Reservation.class);
        });
    }

}