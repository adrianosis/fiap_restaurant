package br.com.fiap.fiaprestaurant.reviews.config;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewsGateway;
import br.com.fiap.fiaprestaurant.reviews.application.usecases.CreateReviewsUseCase;
import br.com.fiap.fiaprestaurant.reviews.infra.gateways.ReviewsEntityMapper;
import br.com.fiap.fiaprestaurant.reviews.infra.gateways.ReviewsRepositoryGateway;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewsConfig {

    @Bean
    CreateReviewsUseCase createReviewUseCase(ReviewsGateway reviewGateway) {
        return new CreateReviewsUseCase(reviewGateway);
    }

    @Bean
    ReviewsRepositoryGateway createReviewRepositoryGateway(ReviewsRepository repository, ReviewsEntityMapper mapper){
        return new ReviewsRepositoryGateway(repository, mapper);
    }

    @Bean
    ReviewsEntityMapper createReviewMapper(){
        return new ReviewsEntityMapper();
    }
}
