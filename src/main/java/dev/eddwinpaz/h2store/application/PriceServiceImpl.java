package dev.eddwinpaz.h2store.application;

import dev.eddwinpaz.h2store.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceServiceImpl {
    Iterable<Price> filterByDate(LocalDateTime dateTime, Integer brandId, Integer productId);
}
