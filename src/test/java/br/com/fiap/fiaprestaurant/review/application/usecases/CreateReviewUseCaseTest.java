package br.com.fiap.fiaprestaurant.review.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.usecases.CreateCustomerUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindReservationByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReview;
import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReviewInput;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateReviewUseCaseTest {

    private CreateReviewUseCase createReviewUseCase;

    @Mock
    private FindReservationByIdUseCase findReservationByIdUseCase;


    @Mock
    private ReviewGateway reviewGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createReviewUseCase = new CreateReviewUseCase(reviewGateway, findReservationByIdUseCase);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void shouldCreateReview() {
        // Arrange
        var reviewInput = createReviewInput();
        var review = createReview();
        var reservation = mock(Reservation.class);

        when(reservation.getId()).thenReturn(1L);
        when(reservation.getStatus()).thenReturn(ReservationStatus.COMPLETED);
        when(findReservationByIdUseCase.execute(reviewInput.getReservationId())).thenReturn(reservation);

        when(reviewGateway.create(any(Review.class)))
                .thenAnswer(i -> {
                    Review createdReview = i.getArgument(0);
                    createdReview.setReservation(reservation);
                    return createdReview;
                });

        // Act
        var savedReview = createReviewUseCase.execute(reviewInput);

        // Assert
        assertThat(savedReview)
                .isInstanceOf(Review.class)
                .isNotNull();
        assertThat(savedReview.getId()).isNotNull();
        assertThat(savedReview.getScore()).isEqualTo(reviewInput.getScore());
        assertThat(savedReview.getComment()).isEqualTo(reviewInput.getComment());
        assertThat(savedReview.getReservation()).isEqualTo(reservation);
        verify(reviewGateway, times(1)).create(any(Review.class));
    }



}
