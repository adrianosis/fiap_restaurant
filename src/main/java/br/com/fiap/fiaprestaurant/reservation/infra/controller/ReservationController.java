package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.application.usecases.ChangeReservationStatusUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllCompletedReservationsByCustomerIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.ReserveRestaurantUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReserveRestaurantUseCase reserveRestaurantUseCase;
    private final ChangeReservationStatusUseCase changeReservationStatusUseCase;
    private final FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
    private final FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase;

    @PostMapping
    public ResponseEntity<ReservationDto> reserve(@RequestBody ReserveRestaurantRequestDto requestDto) throws Exception {
        Reservation reservation = reserveRestaurantUseCase.execute(requestDto.toInput());

        return new ResponseEntity<>(ReservationDto.toDto(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> change(@PathVariable long reservationId,
                                                 @RequestBody ChangeReservationRequestDto requestDto) throws Exception {
        Reservation reservation = changeReservationStatusUseCase.execute(
                reservationId, requestDto.getStatus(), requestDto.getTableTag()
        );

        return new ResponseEntity<>(ReservationDto.toDto(reservation), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/opened")
    public ResponseEntity<List<ReservationDto>> findAllOpenedReservationsByRestaurantAndReservationDateTimeId(@PathVariable long restaurantId,
                                                                                                              @RequestParam LocalDateTime startDateTime,
                                                                                                              @RequestParam LocalDateTime endDateTime) {
        List<Reservation> reservations = findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase.execute(
                restaurantId, startDateTime, endDateTime);
        return new ResponseEntity<>(ReservationDto.toListDto(reservations), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/completed")
    public ResponseEntity<List<ReservationDto>> findAllCompletedReservationsByCustomerIdUseCase(@PathVariable long customerId) {
        List<Reservation> reservations = findAllCompletedReservationsByCustomerIdUseCase.execute(customerId);
        return new ResponseEntity<>(ReservationDto.toListDto(reservations), HttpStatus.OK);
    }

}
