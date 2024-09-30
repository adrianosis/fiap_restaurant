package br.com.fiap.fiaprestaurant.review.infra.persistance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservationEntity;
import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReviewEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewEntityRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (openMocks != null) {
            openMocks.close();
        }
    }

    @Test
    void shouldSaveReview() {
        // Arrange
        var reservation = createReservationEntity(); // Crie a ReservationEntity
        var review = createReviewEntity(reservation); // Passe a ReservationEntity como argumento

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
        var reservation = createReservationEntity(); // Crie a ReservationEntity
        var review = createReviewEntity(reservation); // Passe a ReservationEntity como argumento
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
        // Arrange
        Long id = 1L;
        doNothing().when(reviewRepository).deleteById(any(Long.class));

        // Act
        reviewRepository.deleteById(id);

        // Assert
        verify(reviewRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllReviews() {
        // Arrange
        var reservation1 = createReservationEntity(); // Crie a ReservationEntity para review1
        var reservation2 = createReservationEntity(); // Crie outra ReservationEntity para review2
        var review1 = createReviewEntity(reservation1); // Passe a ReservationEntity como argumento
        review1.setId(1L);
        var review2 = createReviewEntity(reservation2); // Passe a ReservationEntity como argumento
        review2.setId(2L);

        var listReviews = Arrays.asList(review1, review2);
        when(reviewRepository.findAll()).thenReturn(listReviews);

        // Act
        var foundReviews = reviewRepository.findAll();

        // Assert
        assertThat(foundReviews)
                .hasSize(2)
                .containsExactlyInAnyOrder(review1, review2);
        verify(reviewRepository, times(1)).findAll();
    }
}
