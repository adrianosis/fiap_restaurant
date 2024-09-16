package br.com.fiap.fiaprestaurant.restaurant.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query("select r from RestaurantEntity r " +
           "where r.name like :name " +
           "and (r.address.street like :location " +
           "or r.address.district like :location " +
           "or r.address.city like :location) " +
           "and r.kitchenType like :type " +
           "order by r.name")
    List<RestaurantEntity> findAllByNameOrLocationOrType(String name, String location, String type);

}
