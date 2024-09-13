package br.com.fiap.fiaprestaurant.reviews.infra.controller;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.restaurant.infra.controller.SaveRestaurantDto;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;

public class ReviewsDto {

    private Long id;
    private int score;
    private String comment;
    private SaveRestaurantDto restaurant;
    private Customer customer;

    public Reviews toDomain() {
        return new Reviews(
                this.id,
                this.score,
                this.comment,
                restaurant.toDomain(),
                this.customer
        );
    }
}
