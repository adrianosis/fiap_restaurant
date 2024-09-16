package br.com.fiap.fiaprestaurant.reviews.application.usecases;


import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindReservationByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewsGateway;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateReviewsUseCase {

    private final ReviewsGateway reviewsGateway;
    private final FindReservationByIdUseCase findReservationByIdUseCase;

    public Reviews execute(Reviews reviews) throws Exception {
        Reservation reservation = findReservationByIdUseCase.execute(0L);

        return reviewsGateway.create(reviews);
    }

}
