package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Request;
import com.golubovich.project_trpo_tofi.model.RequestStatus;
import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.RequestRepository;
import com.golubovich.project_trpo_tofi.service.api.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserServiceImpl userService;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, UserServiceImpl userService) {
        this.requestRepository = requestRepository;
        this.userService = userService;
    }

    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public List<Request> findAll() {
        return (List<Request>) requestRepository.findAll();
    }

    public void create(Request request) {
        request.setRequestStatus(RequestStatus.NEW);
        request.setCredit(request.getCreditTermRateVariant().getCredit());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        request.setUser(user);
        request.setId(null);

        requestRepository.save(request);
    }

    public void update(Request request) {
        Request old = requestRepository.findById(request.getId()).orElse(null);
        if (old != null) {
            request.setRequestStatus(old.getRequestStatus());
            request.setCredit(old.getCredit());
            request.setUser(old.getUser());
        }

        requestRepository.save(request);
    }

    public void deleteById(Long id) {
        Request request = requestRepository.findById(id).orElse(null);
        if (request != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userService.findByEmail(authentication.getName()).getRole() == Role.USER) {
                request.setRequestStatus(RequestStatus.DELETED);
                requestRepository.save(request);
            }
            else {
                requestRepository.deleteById(id);
            }
        }
    }
}
