package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
class ReserveRestaurantUseCaseIT {

    @Autowired
    private ReserveRestaurantUseCase reserveRestaurantUseCase;

    @Test
    void shouldReserveRestaurant() {
    }

}