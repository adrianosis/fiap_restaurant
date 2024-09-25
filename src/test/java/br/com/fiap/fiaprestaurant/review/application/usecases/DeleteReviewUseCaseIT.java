package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReview;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class DeleteReviewUseCaseIT {

    private RemoveReviewByIdUseCase removeReviewByIdUseCase;

    @Mock
    private ReviewGateway reviewGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        removeReviewByIdUseCase = new RemoveReviewByIdUseCase( reviewGateway);
    }

    @Test
    void shouldDeleteCustomerById() {
        // Arrange
        var id = 1L;
        var review = createReview();
        review.setId(id);
        when(reviewGateway.findReviewById(id))
                .thenReturn(review);
        doNothing()
                .when(reviewGateway).removeReviewById(id);

        // Act
        removeReviewByIdUseCase.execute(id);

        // Assert
        verify(reviewGateway, times(1)).removeReviewById(any(Long.class));
    }


}
