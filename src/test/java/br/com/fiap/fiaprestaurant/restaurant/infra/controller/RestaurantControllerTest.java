package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.restaurant.utils.JsonHelper.asJsonString;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;
    @Mock
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    @Mock
    private FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        RestaurantController restaurantController = new RestaurantController(createRestaurantUseCase,
                findRestaurantByIdUseCase, findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateRestaurant() throws Exception {
        var restaurantRequest = createRestaurantRequest();

        when(createRestaurantUseCase.execute(any(Restaurant.class)))
                .thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andExpect(status().isCreated());
        verify(createRestaurantUseCase, times(1)).execute(any(Restaurant.class));
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        var id = 1L;
        var restaurant = createRestaurant();
        restaurant.setId(id);

        when(findRestaurantByIdUseCase.execute(id)).thenReturn(restaurant);

        mockMvc.perform(get("/restaurant/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurant.getId()))
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.kitchenType").value(restaurant.getKitchenType()))
                .andExpect(jsonPath("$.capacity").value(restaurant.getCapacity()))
                .andExpect(jsonPath("$.openingTime").value(restaurant.getOpeningTime().format(DateTimeFormatter.ISO_TIME)))
                .andExpect(jsonPath("$.closingTime").value(restaurant.getClosingTime().format(DateTimeFormatter.ISO_TIME)))
                .andExpect(jsonPath("$.street").value(restaurant.getAddress().getStreet()))
                .andExpect(jsonPath("$.number").value(restaurant.getAddress().getNumber()))
                .andExpect(jsonPath("$.complement").value(restaurant.getAddress().getComplement()))
                .andExpect(jsonPath("$.district").value(restaurant.getAddress().getDistrict()))
                .andExpect(jsonPath("$.city").value(restaurant.getAddress().getCity()))
                .andExpect(jsonPath("$.state").value(restaurant.getAddress().getState()))
                .andExpect(jsonPath("$.postalCode").value(restaurant.getAddress().getPostalCode()));

        verify(findRestaurantByIdUseCase, times(1)).execute(any(Long.class));
    }

    @Test
    void shouldFindAllRestaurantsByNameOrLocationOrKitchenType() throws Exception {
        // Arrange
        var name = "%";
        var location = "%";
        var kitchenType = "PIZZARIA";

        var restaurant1 = createRestaurant();
        var restaurant2 = createRestaurant();
        var listRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(any(String.class), any(String.class), any(String.class)))
                .thenReturn(listRestaurants);

        // Act
        mockMvc.perform(get("/restaurant").queryParam("name", name)
                        .queryParam("location", location)
                        .queryParam("kitchenType", kitchenType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(restaurant1.getId()))
                .andExpect(jsonPath("$[0].name").value(restaurant1.getName()))
                .andExpect(jsonPath("$[0].kitchenType").value(restaurant1.getKitchenType()))
                .andExpect(jsonPath("$[0].capacity").value(restaurant1.getCapacity()))
                .andExpect(jsonPath("$[0].openingTime").value(restaurant1.getOpeningTime().format(DateTimeFormatter.ISO_TIME)))
                .andExpect(jsonPath("$[0].closingTime").value(restaurant1.getClosingTime().format(DateTimeFormatter.ISO_TIME)))
                .andExpect(jsonPath("$[0].street").value(restaurant1.getAddress().getStreet()))
                .andExpect(jsonPath("$[0].number").value(restaurant1.getAddress().getNumber()))
                .andExpect(jsonPath("$[0].complement").value(restaurant1.getAddress().getComplement()))
                .andExpect(jsonPath("$[0].district").value(restaurant1.getAddress().getDistrict()))
                .andExpect(jsonPath("$[0].city").value(restaurant1.getAddress().getCity()))
                .andExpect(jsonPath("$[0].state").value(restaurant1.getAddress().getState()))
                .andExpect(jsonPath("$[0].postalCode").value(restaurant1.getAddress().getPostalCode()));

        // Assert
        verify(findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase, times(1)).execute(name, location, kitchenType);
    }

}
