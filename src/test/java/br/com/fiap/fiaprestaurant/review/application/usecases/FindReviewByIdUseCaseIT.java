package br.com.fiap.fiaprestaurant.review.application.usecases;


import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
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

    @Test
    void shouldFindReviewById() {
        // Arrange
        var review = ReviewHelper.saveRestaurant(reviewGateway);
        // Act
        var foundReview = findReviewByIdUseCase.execute(review.getId());
        // Assert
        assertThat(foundReview)
                .isNotNull()
                .isInstanceOf(Customer.class);
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
