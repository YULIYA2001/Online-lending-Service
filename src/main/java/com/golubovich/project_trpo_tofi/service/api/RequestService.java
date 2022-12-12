package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Request;

import java.util.List;

public interface RequestService {

    Request findById(Long id);

    List<Request> findAll();

    void create(Request request);

    void update(Request request);

    void deleteById(Long id);
}
