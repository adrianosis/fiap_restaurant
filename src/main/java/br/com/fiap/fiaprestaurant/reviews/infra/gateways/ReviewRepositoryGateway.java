package br.com.fiap.fiaprestaurant.reviews.infra.gateways;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewEntity;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ReviewRepositoryGateway implements ReviewGateway {

    private final ReviewRepository reviewRepository;
    private final ReviewsEntityMapper reviewsEntityMapper;

    @Override
    public Review create(Review review) {
        ReviewEntity entity = reviewsEntityMapper.toEntity(review);
        reviewRepository.save(entity);

        return reviewsEntityMapper.toDomain(entity);
    }

    @Override
    public List<Review> findAllReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findAllByRestaurantId(restaurantId)
                .stream().map(reviewsEntityMapper::toDomain).toList();
    }

    @Override
    public Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewsEntityMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    @Override
    public void removeReviewById(Long reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        reviewRepository.deleteById(review.getId());
    }
}
