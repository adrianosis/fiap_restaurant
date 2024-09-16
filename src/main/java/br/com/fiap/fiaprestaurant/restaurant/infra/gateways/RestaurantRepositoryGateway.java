package br.com.fiap.fiaprestaurant.restaurant.infra.gateways;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository repository;
    private final RestaurantEntityMapper mapper;

    @Override
    public Restaurant create(Restaurant restaurant) {
        RestaurantEntity entity = mapper.toEntity(restaurant);
        entity = repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Restaurant findById(long restaurantId) {
        return repository.findById(restaurantId)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }

    @Override
    public List<Restaurant> findAllByNameOrLocationOrType(String name, String location, String type) {
        return repository.findAllByNameOrLocationOrType(name, location, type).stream().map(mapper::toDomain).toList();
    }

}
