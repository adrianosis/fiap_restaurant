package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReview;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllReviewByCustomerIdUseCaseTest {

    private FindAllReviewsByCustomerIdUseCase findAllReviewsByCustomerIdUseCase;

    @Mock
    private ReviewGateway reviewGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllReviewsByCustomerIdUseCase = new FindAllReviewsByCustomerIdUseCase(reviewGateway);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    void shouldFindAllReviewsByCustomerId() {
        // Arrange
        long customerId = 1l;
        var review = createReview();
        when(reviewGateway.findAllReviewsByCustomerId(any(Long.class)))
                .thenReturn(List.of(review));

        // Act
        var reviewsFound = findAllReviewsByCustomerIdUseCase.execute(customerId);

        // Assert
        verify(reviewGateway, times(1)).findAllReviewsByCustomerId(customerId);
        assertThat(reviewsFound).hasSize(1);
        var reviewFound = reviewsFound.get(0);
        assertThat(reviewFound).isEqualTo(review);
        assertThat(reviewFound.getId()).isEqualTo(review.getId());
        assertThat(reviewFound.getScore()).isEqualTo(review.getScore());
        assertThat(reviewFound.getComment()).isEqualTo(review.getComment());
        assertThat(reviewFound.getReservation()).isEqualTo(review.getReservation());
    }
}
