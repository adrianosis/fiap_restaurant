package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllReviewByRestaurantIdUseCaseTest {

    private FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase;

    @Mock
    private ReviewGateway reviewGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllReviewsByRestaurantIdUseCase = new FindAllReviewsByRestaurantIdUseCase(reviewGateway);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    void shouldFindAllReviewsByRestaurantId() {
        // Arrange
        long restaurantId = 1L;
        Address address = new Address("Rua Exemplo", "123", "Apto 4", "Centro", "Cidade Exemplo", "Estado Exemplo", "12345-678");// Preencha com dados válidos

        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante Exemplo", "Italiano", 50,
                LocalTime.of(11, 0), LocalTime.of(23, 0), address);

        Customer customer = new Customer(1L, "João Silva", "joao.silva@email.com");

        Reservation reservation = new Reservation(LocalDateTime.now(), 4, restaurant, customer);
        var review = new Review(5, "Ótima comida!", reservation);

        when(reviewGateway.findAllReviewsByRestaurantId(any(Long.class)))
                .thenReturn(List.of(review));

        var reviewsFound = findAllReviewsByRestaurantIdUseCase.execute(restaurantId);

        verify(reviewGateway, times(1)).findAllReviewsByRestaurantId(restaurantId);
        assertThat(reviewsFound).hasSize(1);
        var reviewFound = reviewsFound.get(0);
        assertThat(reviewFound).isEqualTo(review);
        assertThat(reviewFound.getId()).isEqualTo(review.getId());
        assertThat(reviewFound.getScore()).isEqualTo(review.getScore());
        assertThat(reviewFound.getComment()).isEqualTo(review.getComment());
        assertThat(reviewFound.getReservation()).isEqualTo(review.getReservation());
    }
}
