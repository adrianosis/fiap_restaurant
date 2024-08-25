package br.com.fiap.fiaprestaurant.restaurant.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
