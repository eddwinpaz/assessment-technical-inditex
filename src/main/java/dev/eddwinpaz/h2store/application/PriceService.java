package dev.eddwinpaz.h2store.application;

import dev.eddwinpaz.h2store.domain.model.Price;
import dev.eddwinpaz.h2store.domain.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceService implements PriceServiceImpl {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    public Iterable<Price> filterByDate(LocalDateTime dateTime, Integer brandId, Integer productId){
        return priceRepository.filterByDate(dateTime, brandId, productId);
    }
}
