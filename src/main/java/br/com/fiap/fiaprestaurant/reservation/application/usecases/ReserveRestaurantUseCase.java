package br.com.fiap.fiaprestaurant.reservation.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReserveRestaurantUseCase {

    private final ReservationGateway reservationGateway;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public Reservation execute(ReserveRestaurantInput input) throws Exception {
        long reservationCount = reservationGateway.countByCurrentDateTimeAndRestaurantId(input.getReservationDateTime(), input.getRestaurantId());

        Restaurant restaurant = findRestaurantByIdUseCase.execute(input.getRestaurantId());
        Customer customer = findCustomerByIdUseCase.execute(input.getCustomerId());

        Reservation reservation = new Reservation(input.getReservationDateTime(), input.getGuests(), restaurant, customer);
        reservation.confirmReserve(reservationCount);

        reservationGateway.create(reservation);

        return reservation;
    }

//    public Reservation changeStatus(long reservationId, ReservationStatus status, String tableTag) {
//        Reservation reservation = reservationGateway.findById(reservationId);
//        reservation.changeStatus(status, tableTag);
//
//        return reservation;
//    }

//    public List<Reservation> findAllFinishedReservationsByCustomerId(long customerId) {
//        return reservationGateway.findAllFinishedReservationsByCustomerId(customerId);
//    }

}
