package br.com.fiap.fiaprestaurant.review.application.usecases;


import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindReservationByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateReviewUseCase {

    private final ReviewGateway reviewGateway;
    private final FindReservationByIdUseCase findReservationByIdUseCase;

    public Review execute(ReviewInput reviewInput) throws RestaurantException {
        Reservation reservation = findReservationByIdUseCase.execute(reviewInput.getReservationId());

        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new RestaurantException("You only can evaluate restaurants with completed reservations.");
        }

        Review review = new Review(reviewInput.getScore(), reviewInput.getComment(), reservation);

        return reviewGateway.create(review);
    }

}
