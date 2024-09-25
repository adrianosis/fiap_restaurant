package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReview;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindReviewByIdUseCaseTest {

    private FindReviewByIdUseCase findReviewByIdUseCase;

    @Mock
    private ReviewGateway reviewGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findReviewByIdUseCase = new FindReviewByIdUseCase( reviewGateway);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    void shouldFindReviewById() {
        // Arrange
        var id = 1L;
        var review = createReview();
        when(reviewGateway.findReviewById(any(Long.class)))
                .thenReturn(review);

        // Act
        var reviewFound = findReviewByIdUseCase.execute(id);

        // Assert
        verify(reviewGateway, times(1))
                .findReviewById(id);
        assertThat(reviewFound)
                .isEqualTo(review);
        assertThat(review.getId())
                .isEqualTo(reviewFound.getId());
        assertThat(review.getScore())
                .isEqualTo(reviewFound.getScore());
        assertThat(review.getComment())
                .isEqualTo(reviewFound.getComment());
        assertThat(review.getReservation())
                .isEqualTo(reviewFound.getReservation());

    }
}
