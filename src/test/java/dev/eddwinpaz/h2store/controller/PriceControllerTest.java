package dev.eddwinpaz.h2store.controller;

import dev.eddwinpaz.h2store.domain.model.Price;
import dev.eddwinpaz.h2store.domain.repository.PriceRepository;
import dev.eddwinpaz.h2store.infrastructure.persistence.JpaPriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaPriceRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        List<Price> prices = List.of(
                new Price(null, 1, LocalDateTime.parse("2020-06-14T00:00:00", formatter), LocalDateTime.parse("2020-12-31T23:59:59", formatter), 1, 35455, 0, 35.50, "EUR"),
                new Price(null, 1, LocalDateTime.parse("2020-06-14T15:00:00", formatter), LocalDateTime.parse("2020-06-14T18:30:00", formatter), 2, 35455, 1, 25.45, "EUR"),
                new Price(null, 1, LocalDateTime.parse("2020-06-15T00:00:00", formatter), LocalDateTime.parse("2020-06-15T11:00:00", formatter), 3, 35455, 1, 30.50, "EUR"),
                new Price(null, 1, LocalDateTime.parse("2020-06-15T16:00:00", formatter), LocalDateTime.parse("2020-12-31T23:59:59", formatter), 4, 35455, 1, 38.95, "EUR"),
                new Price(null, 1, LocalDateTime.parse("2020-01-14T00:00:00", formatter), LocalDateTime.parse("2020-12-31T23:59:59", formatter), 4, 35455, 1, 38.95, "EUR")
        );

        repository.saveAll(prices);

    }

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    private static Stream<TestParams> provideParams() {
        return Stream.of(
                new TestParams(LocalDateTime.of(2020, 6, 14, 10, 0, 0), 1, 35455, status().isOk(), "[{\"id\":9,\"brandId\":1,\"startDate\":\"2020-01-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"productId\":35455,\"price\":38.95}]"),
                new TestParams(LocalDateTime.of(2020, 6, 14, 16, 0, 0), 1, 35455, status().isOk(), "[{\"id\":11,\"brandId\":1,\"startDate\":\"2020-06-14T15:00:00\",\"endDate\":\"2020-06-14T18:30:00\",\"productId\":35455,\"price\":25.45}]"),
                new TestParams(LocalDateTime.of(2020, 6, 14, 21, 0, 0), 1, 35455, status().isOk(), "[{\"id\":19,\"brandId\":1,\"startDate\":\"2020-01-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"productId\":35455,\"price\":38.95}]"),
                new TestParams(LocalDateTime.of(2020, 6, 15, 10, 0, 0), 1, 35455, status().isOk(), "[{\"id\":22,\"brandId\":1,\"startDate\":\"2020-06-15T00:00:00\",\"endDate\":\"2020-06-15T11:00:00\",\"productId\":35455,\"price\":30.5}]"),
                new TestParams(LocalDateTime.of(2020, 6, 16, 21, 0, 0), 1, 35455, status().isOk(), "[{\"id\":28,\"brandId\":1,\"startDate\":\"2020-06-15T16:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"productId\":35455,\"price\":38.95}]"),
                new TestParams(LocalDateTime.of(2020, 6, 16, 21, 0, 0), 1, 12345, status().isNotFound(), "[]")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    void testSearchEndpoint(TestParams params) throws Exception {

        mockMvc.perform(get("/api/price")
                        .param("date", params.dateTime.toString())
                        .param("brandId", params.brandId.toString())
                        .param("productId", params.productId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(params.statusCode)
                .andExpect(content().json(params.expectedResponse));

        String message = String.format("petición a las %s del dia  %s del producto %d para la brand %d",params.dateTime.getHour(), params.dateTime.getDayOfMonth(), params.productId, params.brandId);
        System.out.println(message);

    }

    private static class TestParams {
        LocalDateTime dateTime;
        Integer brandId;
        Integer productId;

        String expectedResponse;

        ResultMatcher statusCode;

        public TestParams(LocalDateTime dateTime, Integer brandId, Integer productId, ResultMatcher statusCode, String expectedResponse) {
            this.dateTime = dateTime;
            this.brandId = brandId;
            this.productId = productId;
            this.statusCode = statusCode;
            this.expectedResponse = expectedResponse;
        }
    }
}
