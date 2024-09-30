package br.com.fiap.fiaprestaurant.review.application.usecases;



import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.review.utils.ReviewHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class FindReviewByIdUseCaseIT {

    @Autowired
    private ReviewGateway reviewGateway;

    @Autowired
    private FindReviewByIdUseCase findReviewByIdUseCase;

    @Autowired
    private ReservationGateway reservationGateway;


    @Test
    void shouldFindReviewById() {
        // Arrange
        Review review = ReviewHelper.createReviewToFind(reservationGateway, reviewGateway);
        long reviewId = review.getId();

        // Act
        var foundReview = findReviewByIdUseCase.execute(reviewId);

        // Assert
        assertThat(foundReview)
                .isNotNull()
                .isInstanceOf(Review.class);
        assertThat(foundReview.getId())
                .isEqualTo(review.getId());
        assertThat(foundReview.getScore())
                .isEqualTo(review.getScore());
        assertThat(foundReview.getComment())
                .isEqualTo(review.getComment());
        assertThat(foundReview.getReservation())
                .isEqualTo(review.getReservation());
    }
}
