package br.com.fiap.fiaprestaurant.reviews.infra.gateways;

import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationEntityMapper;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewsEntityMapper {

    private final ReservationEntityMapper reservationEntityMapper;

    public ReviewEntity toEntity(Review review) {
        return new ReviewEntity(
                review.getId(),
                review.getScore(),
                review.getComment(),
                reservationEntityMapper.toEntity(review.getReservation())
        );
    }

    public Review toDomain(ReviewEntity review) {
        return new Review(
                review.getId(),
                review.getScore(),
                review.getComment(),
                reservationEntityMapper.toDomain(review.getReservation())
        );
    }
}
