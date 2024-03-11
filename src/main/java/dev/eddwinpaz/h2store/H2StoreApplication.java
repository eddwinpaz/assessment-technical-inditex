package dev.eddwinpaz.h2store;

import dev.eddwinpaz.h2store.domain.model.Price;
import dev.eddwinpaz.h2store.domain.repository.PriceRepository;
import dev.eddwinpaz.h2store.infrastructure.persistence.JpaPriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class H2StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2StoreApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(JpaPriceRepository repository){
		return args -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

			repository.save(new Price(null, 1, LocalDateTime.parse("2020-06-14T00:00:00",formatter), LocalDateTime.parse("2020-12-31T23:59:59",formatter), 1, 35455, 0, 35.50, "EUR"));
			repository.save(new Price(null, 1, LocalDateTime.parse("2020-06-14T15:00:00",formatter), LocalDateTime.parse("2020-06-14T18:30:00",formatter), 2, 35455, 1, 25.45, "EUR"));
			repository.save(new Price(null, 1, LocalDateTime.parse("2020-06-15T00:00:00",formatter), LocalDateTime.parse("2020-06-15T11:00:00",formatter), 3, 35455, 1, 30.50, "EUR"));
			repository.save(new Price(null, 1, LocalDateTime.parse("2020-06-15T16:00:00",formatter), LocalDateTime.parse("2020-12-31T23:59:59",formatter), 4, 35455, 1, 38.95, "EUR"));

		};
	}

}
