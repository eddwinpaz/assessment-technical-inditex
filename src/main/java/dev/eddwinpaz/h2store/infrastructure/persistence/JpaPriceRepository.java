package dev.eddwinpaz.h2store.infrastructure.persistence;

import dev.eddwinpaz.h2store.domain.model.Price;
import dev.eddwinpaz.h2store.domain.repository.PriceRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceRepository extends PriceRepository, CrudRepository<Price, Integer> {}
