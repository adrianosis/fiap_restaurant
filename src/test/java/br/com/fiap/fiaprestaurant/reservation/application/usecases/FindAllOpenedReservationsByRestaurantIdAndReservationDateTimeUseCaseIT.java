package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
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
public class FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCaseIT {

    @Autowired
    private FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;

    @Test
    void shouldFindAllCompletedReservationsByCustomerIdUseCase() {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of (2024,9,15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of (2024,9,15, 20, 0);

        // Act
        var reservations = findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase.execute(
                restaurantId, startDateTime, endDateTime);

        // Assert
        assertThat(reservations).hasSize(2).allSatisfy(reservation -> {
            assertThat(reservation).isNotNull();
            assertThat(reservation).isInstanceOf(Reservation.class);
        });
    }

}