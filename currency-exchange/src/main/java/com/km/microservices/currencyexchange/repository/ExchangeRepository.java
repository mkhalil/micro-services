package com.km.microservices.currencyexchange.repository;


import com.km.microservices.currencyexchange.entities.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Optional<Exchange> findByFromAndTo(String from, String to);

}
