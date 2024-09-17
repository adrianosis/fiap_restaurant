package br.com.fiap.fiaprestaurant.reviews.application.usecases;


import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindReservationByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.reviews.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateReviewUseCase {

    private final ReviewGateway reviewGateway;
    private final FindReservationByIdUseCase findReservationByIdUseCase;

    public Review execute(ReviewInput reviewInput) throws Exception {
        Reservation reservation = findReservationByIdUseCase.execute(reviewInput.getReservationId());

        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new Exception("You only can evaluate restaurants with completed reservations.");
        }

        Review review = new Review(reviewInput.getScore(), reviewInput.getComment(), reservation);

        return reviewGateway.create(review);
    }

}
