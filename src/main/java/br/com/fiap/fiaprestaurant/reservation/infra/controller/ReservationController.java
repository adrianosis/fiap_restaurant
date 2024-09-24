package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.application.usecases.ChangeReservationStatusUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllCompletedReservationsByCustomerIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.ReserveRestaurantUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation", description = "Operations related to restaurant reservations, including creating, modifying, and retrieving reservations.")
public class ReservationController {

    private final ReserveRestaurantUseCase reserveRestaurantUseCase;
    private final ChangeReservationStatusUseCase changeReservationStatusUseCase;
    private final FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
    private final FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase;

    @PostMapping
    @Operation(summary = "Create a new reservation", description = "Reserves a restaurant table based on the provided data.")
    public ResponseEntity<ReservationDto> reserve(@RequestBody ReserveRestaurantRequestDto requestDto) throws Exception {
        Reservation reservation = reserveRestaurantUseCase.execute(requestDto.toInput());

        return new ResponseEntity<>(ReservationDto.toDto(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}")
    @Operation(summary = "Change reservation status", description = "Updates the reservation status and assigns a table tag to the reservation.")
    public ResponseEntity<ReservationDto> change(@PathVariable long reservationId,
                                                 @RequestBody ChangeReservationRequestDto requestDto) throws Exception {
        Reservation reservation = changeReservationStatusUseCase.execute(
                reservationId, requestDto.getStatus(), requestDto.getTableTag()
        );

        return new ResponseEntity<>(ReservationDto.toDto(reservation), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/opened")
    @Operation(summary = "Find opened reservations by restaurant",
            description = "Retrieves all opened reservations for a specific restaurant within the given date and time range.")
    public ResponseEntity<List<ReservationDto>> findAllOpenedReservationsByRestaurantAndReservationDateTimeId(@PathVariable long restaurantId,
                                                                                                              @RequestParam LocalDateTime startDateTime,
                                                                                                              @RequestParam LocalDateTime endDateTime) {
        List<Reservation> reservations = findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase.execute(
                restaurantId, startDateTime, endDateTime);
        return new ResponseEntity<>(ReservationDto.toListDto(reservations), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/completed")
    @Operation(summary = "Find completed reservations by customer",
            description = "Retrieves all completed reservations made by a specific customer.")
    public ResponseEntity<List<ReservationDto>> findAllCompletedReservationsByCustomerIdUseCase(@PathVariable long customerId) {
        List<Reservation> reservations = findAllCompletedReservationsByCustomerIdUseCase.execute(customerId);
        return new ResponseEntity<>(ReservationDto.toListDto(reservations), HttpStatus.OK);
    }

}
