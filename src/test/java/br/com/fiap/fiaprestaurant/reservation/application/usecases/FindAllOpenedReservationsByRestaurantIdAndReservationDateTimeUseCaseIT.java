package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCaseIT {



}