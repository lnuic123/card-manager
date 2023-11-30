package com.exercise.card.manager.dao.repository;

import com.exercise.card.manager.dao.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findByName(final String name);

}
