package br.com.fiap.fiaprestaurant.reviews.infra.gateways;

import br.com.fiap.fiaprestaurant.reviews.application.gateways.ReviewsGateway;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewsEntity;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewsRepository;

public class ReviewsRepositoryGateway implements ReviewsGateway {

    private final ReviewsRepository reviewsRepository;
    private final ReviewsEntityMapper reviewsEntityMapper;

    public ReviewsRepositoryGateway(ReviewsRepository reviewsRepository,
                                   ReviewsEntityMapper reviewsEntityMapper) {
        this.reviewsRepository = reviewsRepository;
        this.reviewsEntityMapper = reviewsEntityMapper;
    }

    @Override
    public Reviews create(Reviews review) {
        ReviewsEntity entity = reviewsEntityMapper.toEntity(review);
        reviewsRepository.save(entity);

        return reviewsEntityMapper.toDomain(entity);
    }
}
