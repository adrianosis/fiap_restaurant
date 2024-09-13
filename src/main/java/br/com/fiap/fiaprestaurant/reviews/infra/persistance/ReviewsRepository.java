package br.com.fiap.fiaprestaurant.reviews.infra.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<ReviewsEntity, Long> {
}
