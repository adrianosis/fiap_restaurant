package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.ChangeReservationStatusUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllCompletedReservationsByCustomerIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.ReserveRestaurantUseCase;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.controller.RestaurantController;
import br.com.fiap.fiaprestaurant.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReservation;
import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReserveRestaurantRequest;
import static br.com.fiap.fiaprestaurant.restaurant.utils.JsonHelper.asJsonString;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantRequest;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ReserveRestaurantUseCase reserveRestaurantUseCase;
    @Mock
    private ChangeReservationStatusUseCase changeReservationStatusUseCase;
    @Mock
    private FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase;
    @Mock
    private FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        ReservationController reservationController = new ReservationController(
                reserveRestaurantUseCase, changeReservationStatusUseCase,
                findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase, findAllCompletedReservationsByCustomerIdUseCase
        );

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReserveRestaurant() throws Exception {
        var reserveRestaurantRequest = createReserveRestaurantRequest();
        var reservation = createReservation();

        when(reserveRestaurantUseCase.execute(any(ReserveRestaurantInput.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reserveRestaurantRequest)))
                .andExpect(status().isCreated());
        verify(reserveRestaurantUseCase, times(1)).execute(any(ReserveRestaurantInput.class));
    }

    @Test
    void shouldChangeReservation() throws Exception {
        long reservationId = 1L;
        ChangeReservationRequestDto changeReservationRequest = new ChangeReservationRequestDto(
                ReservationStatus.IN_PROGRESS, "A05"
        );
        var reservation = createReservation();
        reservation.setStatus(ReservationStatus.IN_PROGRESS);
        reservation.setTableTag("A05");

        when(changeReservationStatusUseCase.execute(any(Long.class), any(ReservationStatus.class), any(String.class))).thenReturn(reservation);

        mockMvc.perform(put("/reservation/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(changeReservationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservation.getId()))
                .andExpect(jsonPath("$.status").value(reservation.getStatus().toString()))
                .andExpect(jsonPath("$.tableTag").value(reservation.getTableTag()));

        verify(changeReservationStatusUseCase, times(1)).execute(any(Long.class), any(ReservationStatus.class), any(String.class));
    }

    @Test
    void shouldFindAllOpenedReservationsByRestaurantAndReservationDateTimeId() throws Exception {
        // Arrange
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2024, 9, 15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 9, 15, 20, 0);

        var reservation1 = createReservation();
        var reservation2 = createReservation();
        var reservations = Arrays.asList(reservation1, reservation2);

        when(findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase.execute(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);

        // Act
        mockMvc.perform(get("/reservation/restaurant/{restaurantId}/opened", restaurantId)
                        .queryParam("startDateTime", startDateTime.toString())
                        .queryParam("endDateTime", endDateTime.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reservation1.getId()))
                .andExpect(jsonPath("$[0].reservationDateTime").value(reservation1.getReservationDateTime().format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$[0].guests").value(reservation1.getGuests()))
                .andExpect(jsonPath("$[0].tableTag").value(reservation1.getTableTag()))
                .andExpect(jsonPath("$[0].status").value(reservation1.getStatus().toString()));

        // Assert
        verify(findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase, times(1))
                .execute(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void shouldFindAllCompletedReservationsByCustomerIdUseCase() throws Exception {
        // Arrange
        long customerId = 1L;

        var reservation1 = createReservation();
        var reservation2 = createReservation();
        var reservations = Arrays.asList(reservation1, reservation2);

        when(findAllCompletedReservationsByCustomerIdUseCase.execute(any(Long.class))).thenReturn(reservations);

        // Act
        mockMvc.perform(get("/reservation/customer/{customerId}/completed", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reservation1.getId()))
                .andExpect(jsonPath("$[0].reservationDateTime").value(reservation1.getReservationDateTime().format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$[0].guests").value(reservation1.getGuests()))
                .andExpect(jsonPath("$[0].tableTag").value(reservation1.getTableTag()))
                .andExpect(jsonPath("$[0].status").value(reservation1.getStatus().toString()));

        // Assert
        verify(findAllCompletedReservationsByCustomerIdUseCase, times(1)).execute(any(Long.class));
    }

}