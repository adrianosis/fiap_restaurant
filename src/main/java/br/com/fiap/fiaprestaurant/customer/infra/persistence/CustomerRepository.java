package br.com.fiap.fiaprestaurant.customer.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
