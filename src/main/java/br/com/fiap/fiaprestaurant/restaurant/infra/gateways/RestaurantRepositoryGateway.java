package br.com.fiap.fiaprestaurant.restaurant.infra.gateways;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Restaurant create(Restaurant restaurant) {
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(entity);

        return restaurantEntityMapper.toDomain(entity);
    }

    @Override
    public Restaurant findById(long restaurantId) throws Exception {
        return restaurantRepository.findById(restaurantId)
                .map(restaurantEntityMapper::toDomain);

        return null;
    }

    @Override
    public List<Restaurant> findAllByNameOrLocationOrType(String name, String location, String type) {
        return null;
    }

}
