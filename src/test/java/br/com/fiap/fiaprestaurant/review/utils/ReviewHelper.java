package br.com.fiap.fiaprestaurant.review.utils;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationRepository;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.review.infra.controller.ReviewRequestDto;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewEntity;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewRepository;
import java.time.LocalDateTime;
import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.saveReservation;


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

    public static ReviewEntity createReviewEntity(ReservationEntity reservationEntity) {
        return ReviewEntity.builder()
                .score(5)
                .comment("Comida excelente")
                .reservation(reservationEntity)
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

    public static ReviewEntity saveReviewEntity(ReviewRepository reviewRepository,
                                                ReservationRepository reservationRepository,
                                                RestaurantRepository restaurantRepository,
                                                CustomerRepository customerRepository) {
        RestaurantEntity restaurantEntity = RestaurantHelper.createRestaurantEntity();
        restaurantRepository.save(restaurantEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("John Doe");
        customerEntity.setEmail("john@gmail.com");
        customerRepository.save(customerEntity);

        LocalDateTime reservationDateTime = LocalDateTime.now();
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .status(ReservationStatus.COMPLETED)
                .restaurant(restaurantEntity)
                .reservationDateTime(reservationDateTime)
                .customer(customerEntity)
                .build();

        reservationRepository.save(reservationEntity);

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .score(5)
                .comment("Comida excelente")
                .reservation(reservationEntity)
                .build();

        return reviewRepository.save(reviewEntity);
    }

    public static Review createReviewToFind(ReservationGateway reservationGateway, ReviewGateway reviewGateway) {
        Reservation reservation = saveReservation(reservationGateway);

        Review review = new Review();
        review.setScore(5);
        review.setComment("Ótimo serviço!");
        review.setReservation(reservation);


        Review savedReview = reviewGateway.create(review);

        return savedReview;
    }


    public static Review createReview(Long id, int score, String comment, Reservation reservation) {
        return Review.builder()
                .id(id)
                .score(score)
                .comment(comment)
                .reservation(reservation)
                .build();
    }

}
