package dev.eddwinpaz.h2store.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Price(
        @Id
        Integer id,
        Integer brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        @JsonIgnore
        Integer priceList,
        Integer productId,
        @JsonIgnore
        Integer priority,
        Double price,
        @JsonIgnore
        String curr
) {}
