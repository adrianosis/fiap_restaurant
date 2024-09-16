package br.com.fiap.fiaprestaurant.reservation.application.usecases;

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
@ActiveProfiles("test")
@Transactional
public class ChangeReservationStatusUseCaseIT {

    @Autowired
    private ChangeReservationStatusUseCase changeReservationStatusUseCase;

    @Test
    void shouldChangeReservation() throws Exception {
        long id = 2L;
        var status = ReservationStatus.IN_PROGRESS;
        var tableTag = "A07";

        // Act
        var changedReservation = changeReservationStatusUseCase.execute(id, status, tableTag);

        // Assert
        assertThat(changedReservation).isInstanceOf(Reservation.class).isNotNull();
        assertThat(changedReservation.getId()).isEqualTo(id);
        assertThat(changedReservation.getStatus()).isEqualTo(status);
        assertThat(changedReservation.getStartService().withSecond(0).withNano(0))
                .isEqualTo(LocalDateTime.now().withSecond(0).withNano(0));
        assertThat(changedReservation.getTableTag()).isEqualTo(tableTag);
    }

}