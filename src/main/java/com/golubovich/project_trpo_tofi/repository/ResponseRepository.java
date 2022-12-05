package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Response;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Long> {
    Optional<Response> findById(Long id);
}
