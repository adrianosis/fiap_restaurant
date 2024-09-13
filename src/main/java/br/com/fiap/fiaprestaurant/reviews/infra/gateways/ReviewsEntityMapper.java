package br.com.fiap.fiaprestaurant.reviews.infra.gateways;

import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;
import br.com.fiap.fiaprestaurant.reviews.infra.persistance.ReviewsEntity;

public class ReviewsEntityMapper {


    private final RestaurantEntityMapper restaurantMapper = new RestaurantEntityMapper();
    private final CustomerEntityMapper customerEntityMapper = new CustomerEntityMapper();


    public ReviewsEntity toEntity(Reviews review) {
        return new ReviewsEntity(
                review.getId(),
                review.getScore(),
                review.getComment(),
                restaurantMapper.toEntity(review.getRestaurant()),
                customerEntityMapper.toEntity(review.getCustomer())
        );
    }

    public Reviews toDomain(ReviewsEntity entity) {
        return new Reviews(
                entity.getId(),
                entity.getScore(),
                entity.getComment(),
                restaurantMapper.toDomain(entity.getRestaurant()),
                customerEntityMapper.toDomain(entity.getCustomer())
        );
    }
}
