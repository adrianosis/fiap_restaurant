package br.com.fiap.fiaprestaurant.review.infra.persistance;


import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.review.utils.ReviewHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class ReviewEntityRepositoryIT {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void shouldCreateTable() {
        long registers = reviewRepository.count();
        assertThat(registers).isPositive();
    }

    @Test
    void shouldSaveReview() {
        // Arrange
        var review = ReviewHelper.createReviewEntity();
        // Act
        var savedReview = reviewRepository.save(review);
        // Assert
        assertThat(savedReview)
                .isInstanceOf(Review.class)
                .isNotNull();
        assertThat(savedReview.getScore())
                .isEqualTo(review.getScore());
        assertThat(savedReview.getComment())
                .isEqualTo(review.getComment());
    }

    @Test
    void shouldFindReviewById() {
        // Arrange
        var review = ReviewHelper.saveReviewEntity(reviewRepository);
        var id = review.getId();
        // Act
        var foundReviewOptional = reviewRepository.findById(id);
        // Assert
        assertThat(foundReviewOptional)
                .isPresent()
                .containsSame(review);
    }

    @Test
    void shouldDeleteReviewById() {
        // Arrange
        var review = ReviewHelper.saveReviewEntity(reviewRepository);
        var id = review.getId();
        // Act
        reviewRepository.deleteById(id);
        var foundReviewOptional = reviewRepository.findById(id);
        // Assert
        assertThat(foundReviewOptional)
                .isEmpty();
    }

    @Test
    void shouldFindAllReviews() {
        // Act
        var result = reviewRepository.findAll();
        // Assert
        assertThat(result)
                .isNotEmpty();
    }
}
