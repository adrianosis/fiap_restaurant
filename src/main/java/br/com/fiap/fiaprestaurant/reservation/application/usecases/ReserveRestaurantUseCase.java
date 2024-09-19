package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ReserveRestaurantUseCase {

    private final ReservationGateway reservationGateway;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public Reservation execute(ReserveRestaurantInput input) throws Exception {
        LocalDateTime startDateTime = input.getReservationDateTime().withMinute(0).withSecond(0);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        int reservationCount = reservationGateway.countByRestaurantIdAndReservationDateTime(
                input.getRestaurantId(), startDateTime, endDateTime);

        Restaurant restaurant = findRestaurantByIdUseCase.execute(input.getRestaurantId());
        Customer customer = findCustomerByIdUseCase.execute(input.getCustomerId());

        Reservation reservation = new Reservation(input.getReservationDateTime(), input.getGuests(), restaurant, customer);
        reservation.confirmReserve(reservationCount);

        reservation = reservationGateway.save(reservation);

        return reservation;
    }

}
