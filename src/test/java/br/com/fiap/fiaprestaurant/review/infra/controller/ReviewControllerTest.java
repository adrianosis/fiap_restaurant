package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.review.application.usecases.*;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.review.infra.gateways.ReviewsEntityMapper;
import br.com.fiap.fiaprestaurant.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateReviewUseCase createReviewUseCase;
    @Mock
    private FindReviewByIdUseCase findReviewByIdUseCase;
    @Mock
    private FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase;
    @Mock
    private RemoveReviewByIdUseCase removeReviewByIdUseCase;
    @Mock
    private FindAllReviewsByCustomerIdUseCase findAllReviewsByCustomerIdUseCase;

    ReviewsEntityMapper mapper;

    AutoCloseable openMocks;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ReviewController reviewController = new ReviewController(
                createReviewUseCase,
                findAllReviewsByRestaurantIdUseCase,
                findAllReviewsByCustomerIdUseCase,
                findReviewByIdUseCase,
                removeReviewByIdUseCase
        );
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateReview() throws Exception {

        Address address = new Address("Av. das Nações", "1234", "Apto 56", "Centro", "São Paulo", "SP", "12345-678");
        Restaurant restaurant = new Restaurant(
                0L, // ID padrão
                "Pizza Place",
                "Italian",
                100,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                address
        );

        Customer customer = new Customer("John Doe", "john.doe@example.com");

        Reservation reservation = Reservation.builder()
                .id(1L)
                .reservationDateTime(LocalDateTime.now())
                .guests(4)
                .startService(LocalDateTime.now().plusHours(1))
                .endService(LocalDateTime.now().plusHours(2))
                .tableTag("Table 1")
                .status(ReservationStatus.COMPLETED)
                .restaurant(restaurant)
                .customer(customer)
                .build();

        Review review = new Review();
        review.setComment("Great food!");
        review.setScore(5);
        review.setReservation(reservation);

        when(createReviewUseCase.execute(any(ReviewInput.class))).thenReturn(review);
    }

    @Test
    void shouldFindReviewById() throws Exception {
        Address address = new Address("Av. das Nações", "1234", "Apto 56", "Centro", "São Paulo", "SP", "12345-678");
        Restaurant restaurant = new Restaurant(
                0L, // ID padrão
                "Pizza Place",
                "Italian",
                100,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                address
        );

        Customer customer = new Customer("John Doe", "john.doe@example.com");

        Reservation reservation = Reservation.builder()
                .id(1L)
                .reservationDateTime(LocalDateTime.now())
                .guests(4)
                .startService(LocalDateTime.now().plusHours(1))
                .endService(LocalDateTime.now().plusHours(2))
                .tableTag("Table 1")
                .status(ReservationStatus.COMPLETED)
                .restaurant(restaurant)
                .customer(customer)
                .build();

        var review = new Review();
        review.setId(1L);
        review.setReservation(reservation);

        when(findReviewByIdUseCase.execute(1L)).thenReturn(review);

        mockMvc.perform(get("/review/{reviewId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(review.getId()))
                .andExpect(jsonPath("$.reservationId").value(review.getReservation().getId()));
    }

    @Test
    void shouldDeleteReviewById() throws Exception {
        var id = 1L;
        doNothing().when(removeReviewByIdUseCase).execute(any(Long.class));

        mockMvc.perform(delete("/review/{id}", id))
                .andExpect(status().isNoContent());
        verify(removeReviewByIdUseCase, times(1)).execute(any(Long.class));
    }

    @Test
    void shouldFindAllReviewsByRestaurantId() throws Exception {
        Address address = new Address("Av. das Nações", "1234", "Apto 56", "Centro", "São Paulo", "SP", "12345-678");
        Restaurant restaurant = new Restaurant(
                0L,
                "Pizza Place",
                "Italian",
                100,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                address
        );

        Customer customer = new Customer("John Doe", "john.doe@example.com");

        Reservation reservation = Reservation.builder()
                .id(1L)
                .reservationDateTime(LocalDateTime.now())
                .guests(4)
                .startService(LocalDateTime.now().plusHours(1))
                .endService(LocalDateTime.now().plusHours(2))
                .tableTag("Table 1")
                .status(ReservationStatus.COMPLETED)
                .restaurant(restaurant)
                .customer(customer)
                .build();

        Review review = new Review();
        review.setId(1L);
        review.setReservation(reservation);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);


        when(findAllReviewsByRestaurantIdUseCase.execute(1L)).thenReturn(reviews);

        mockMvc.perform(get("/review/restaurant/{restaurantId}", 1L))
                .andDo(print())  // Imprime a resposta no console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].reservationId").value(1L)); // Verifica se a reserva está presente
    }
}