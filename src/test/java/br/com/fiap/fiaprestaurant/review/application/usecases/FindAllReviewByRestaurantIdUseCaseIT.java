package br.com.fiap.fiaprestaurant.review.application.usecases;


import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindAllReviewByRestaurantIdUseCaseIT {

    @Autowired
    private ReviewGateway reviewGateway;

    @Autowired
    private FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase;

    @Test
    public void testFindAllReviewsByRestaurantId() {
        // Arrange
        long restaurantId = 1L;


        List<Review> reviews = findAllReviewsByRestaurantIdUseCase.execute(restaurantId);


        assertThat(reviews).hasSize(2).allSatisfy(review -> {
            assertThat(review).isNotNull();
            assertThat(review).isInstanceOf(Review.class);
        });
    }
}
