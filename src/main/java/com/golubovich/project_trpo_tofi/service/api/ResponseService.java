package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Response;

public interface ResponseService {

    void createResponseToRequestById(Long requestId);

    Response findByRequestId(Long requestId);
}
