package br.com.fiap.fiaprestaurant.review.application.usecases;


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
public class CreateReviewUseCaseIT {

    @Autowired
    private CreateReviewUseCase createReviewUseCase;

    @Test
    void shouldCreateReview() {
        var review = ReviewHelper.createReviewInput();

        var savedReview = createReviewUseCase.execute(review);

        assertThat(savedReview)
                .isNotNull()
                .isInstanceOf(Review.class);
        assertThat(savedReview.getId())
                .isGreaterThan(0);
        assertThat(savedReview.getScore()).isNotNull().isEqualTo(review.getScore());
        assertThat(savedReview.getComment()).isNotNull().isEqualTo(review.getComment());

        // Corrigido: Acessando o ID da reserva do objeto Reservation
        assertThat(savedReview.getReservation()).isNotNull();
        assertThat(savedReview.getReservation().getId()).isEqualTo(review.getReservationId());
    }

}
