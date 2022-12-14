package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.*;
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
    private BankServiceImpl bankService;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        request.setUser(user);

        requestRepository.save(request);
    }

    public void update(Request request) {
        Request old = requestRepository.findById(request.getId()).orElse(null);
        if (old != null) {
            request.setRequestStatus(old.getRequestStatus());
            request.setUser(old.getUser());
        }

        requestRepository.save(request);
    }

    public void updateStatusReject(Long requestId) {
        Request request = this.findById(requestId);
        if (request.getRequestStatus() == RequestStatus.NEW) {
            request.setRequestStatus(RequestStatus.REJECTED);
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

    public List<Request> findAllByBank() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = userService.findByEmail(authentication.getName());
        if (admin != null) {
            Bank bank = admin.getBank();

            List <Request> bankRequests = requestRepository.findByBankId(bank.getId());
            return bankRequests;
        }
        return null;
    }
}
