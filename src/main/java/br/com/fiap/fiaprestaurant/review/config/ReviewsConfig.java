package br.com.fiap.fiaprestaurant.review.config;

import br.com.fiap.fiaprestaurant.reservation.application.usecases.FindReservationByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationEntityMapper;
import br.com.fiap.fiaprestaurant.review.application.gateways.ReviewGateway;
import br.com.fiap.fiaprestaurant.review.application.usecases.*;
import br.com.fiap.fiaprestaurant.review.infra.gateways.ReviewsEntityMapper;
import br.com.fiap.fiaprestaurant.review.infra.gateways.ReviewRepositoryGateway;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewsConfig {

    @Bean
    CreateReviewUseCase createReviewUseCase(ReviewGateway reviewGateway, FindReservationByIdUseCase findReservationByIdUseCase) {
        return new CreateReviewUseCase(reviewGateway, findReservationByIdUseCase);
    }

    @Bean
    FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase(ReviewGateway reviewGateway) {
        return new FindAllReviewsByRestaurantIdUseCase(reviewGateway);
    }

    @Bean
    FindAllReviewsByCustomerIdUseCase findAllReviewsByCustomerIdUseCase(ReviewGateway reviewGateway) {
        return new FindAllReviewsByCustomerIdUseCase(reviewGateway);
    }

    @Bean
    FindReviewByIdUseCase findReviewByIdUseCase(ReviewGateway reviewGateway){
        return new FindReviewByIdUseCase(reviewGateway);
    }

    @Bean
    RemoveReviewByIdUseCase removeReviewUseCase(ReviewGateway reviewGateway){
        return new RemoveReviewByIdUseCase(reviewGateway);
    }

    @Bean
    ReviewRepositoryGateway createReviewRepositoryGateway(ReviewRepository repository, ReviewsEntityMapper mapper){
        return new ReviewRepositoryGateway(repository, mapper);
    }

    @Bean
    ReviewsEntityMapper createReviewMapper(ReservationEntityMapper reservationEntityMapper){
        return new ReviewsEntityMapper(reservationEntityMapper);
    }
}
