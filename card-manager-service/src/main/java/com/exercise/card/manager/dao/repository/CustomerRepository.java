package com.exercise.card.manager.dao.repository;

import com.exercise.card.manager.dao.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByOib(final String oib);

    long deleteByOib(final String oib);
}
