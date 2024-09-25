package br.com.fiap.fiaprestaurant.review.utils;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.review.infra.controller.ReviewRequestDto;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewEntity;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewRepository;

public class ReviewHelper {

    public static ReviewRequestDto createReviewDTORequest() {
        return new ReviewRequestDto(5, "Comida excelente", 1);
    }

    public static ReviewEntity createReviewEntity(){
        ReservationEntity reservationEntity = ReservationEntity.builder().status(ReservationStatus.COMPLETED).build();

        return ReviewEntity.builder()
                .score(5).comment("Comida excelente").reservation(reservationEntity)
                .build();
    }

    public static ReviewInput createReviewInput() {
        return ReviewInput.builder()
                .score(5).comment("Comida excelente").reservationId(1).build();
    }

    public static Review createReview() {
        Reservation reservation = Reservation.builder().build();

        return new Review(5, "Comida excelente", reservation );
    }

    public static ReviewEntity saveReviewEntity(ReviewRepository reviewRepository) {
        var review = createReviewEntity();
        review.setId(1);
        return reviewRepository.save(review);
    }

    public static Review saveRestaurant(ReviewGateway reviewGateway) {
        var review = createReview();
        review.setId(4L);
        return reviewGateway.create(review);
    }

}
