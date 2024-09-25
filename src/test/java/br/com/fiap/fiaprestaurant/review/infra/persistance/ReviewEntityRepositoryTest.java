package br.com.fiap.fiaprestaurant.review.infra.persistance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReviewEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewEntityRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldSaveReview() {
        // Arrange
        var review = createReviewEntity();

        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(review);

        // Act
        var savedReview = reviewRepository.save(review);

        // Assert
        assertThat(savedReview)
                .isNotNull()
                .isEqualTo(review);
        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
    }

    @Test
    void shouldFindReviewById() {
        // Arrange
        Long id = 1L;
        var review = createReviewEntity();
        review.setId(id);
        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(review));

        // Act
        var foundReviewOptional = reviewRepository.findById(id);

        // Assert
        assertThat(foundReviewOptional).isPresent().containsSame(review);
        foundReviewOptional.ifPresent(foundReviewEntity -> {
            assertThat(foundReviewEntity.getId()).isEqualTo(review.getId());
            assertThat(foundReviewEntity.getScore()).isEqualTo(review.getScore());
            assertThat(foundReviewEntity.getComment()).isEqualTo(review.getComment());
        });
        verify(reviewRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldDeleteReviewById() {

        Long id = 1L;
        doNothing().when(reviewRepository).deleteById(any(Long.class));

        reviewRepository.deleteById(id);

        verify(reviewRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllReviews() {

        var review1 = createReviewEntity();
        review1.setId(1L);
        var review2 = createReviewEntity();
        review2.setId(2L);

        var listReviews = Arrays.asList(review1, review2);
        when(reviewRepository.findAll()).thenReturn(listReviews);

        var foundReviews = reviewRepository.findAll();

        assertThat(foundReviews)
                .hasSize(2)
                .containsExactlyInAnyOrder(review1, review2);
        verify(reviewRepository, times(1)).findAll();
    }
}
