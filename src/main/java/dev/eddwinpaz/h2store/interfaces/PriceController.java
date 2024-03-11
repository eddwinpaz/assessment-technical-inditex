package dev.eddwinpaz.h2store.interfaces;

import dev.eddwinpaz.h2store.domain.model.Price;
import dev.eddwinpaz.h2store.application.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/price")
    public ResponseEntity<?> search(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam("brandId") Integer brandId,
            @RequestParam("productId") Integer productId
            ){
        Iterable<Price> prices = priceService.filterByDate(dateTime, brandId, productId);
        if(!prices.iterator().hasNext()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[]");
        }
        return ResponseEntity.ok(prices);

    }

}
