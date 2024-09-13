package br.com.fiap.fiaprestaurant.reviews.domain.entity;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;


public class Reviews {

    private long id;
    private int score;
    private String comment;
    private Restaurant restaurant;
    private Customer customer;

    public Reviews(long id, int score, String comment, Restaurant restaurant, Customer customer) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() { return customer;}
}
