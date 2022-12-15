package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.AddressPhones;
import com.golubovich.project_trpo_tofi.model.Bank;
import com.golubovich.project_trpo_tofi.model.BankAddress;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.AddressPhonesRepository;
import com.golubovich.project_trpo_tofi.repository.BankAddressRepository;
import com.golubovich.project_trpo_tofi.repository.BankRepository;
import com.golubovich.project_trpo_tofi.service.api.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final BankAddressRepository addressRepository;
    private final AddressPhonesRepository phonesRepository;
    private final UserServiceImpl userService;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, BankAddressRepository addressRepository,
                           AddressPhonesRepository phonesRepository, @Lazy UserServiceImpl userService) {
        this.bankRepository = bankRepository;
        this.addressRepository = addressRepository;
        this.phonesRepository = phonesRepository;
        this.userService = userService;
    }


    public Bank findById(Long id) {
        return bankRepository.findById(id).orElse(null);
    }

    public List<Bank> findAll() {
        return (List<Bank>) bankRepository.findAll();
    }

    public Bank findByAdminEmail(String adminEmail) {
        return bankRepository.findByAdminEmail(adminEmail).orElse(null);
    }

    public void createBank(Bank bank) {
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userService.findByEmail(adminEmail);
        bank.setAdmin(admin);
        bankRepository.save(bank);
    }

    public void addContacts(Long bankId, String address, String[] phones) {
        Bank bank = this.findById(bankId);

        BankAddress bankAddress = new BankAddress(address, bank);
        addressRepository.save(bankAddress);

        for (String phone : phones) {
            if (!phone.equals("")) {
                phonesRepository.save(new AddressPhones(phone, bankAddress));
            }
        }
    }

    public void deleteContacts(Long bankAddressId) {
        addressRepository.deleteById(bankAddressId);
    }

    public boolean update(Bank updatedBank) {
        Bank oldBank = this.findById(updatedBank.getId());

        if (!updatedBank.getName().equals(oldBank.getName())
                && this.bankRepository.findByName(updatedBank.getName()).isPresent()) {
            return false;
        }

        oldBank.setName(updatedBank.getName());
        oldBank.setAboutInfo(updatedBank.getAboutInfo());
        oldBank.setTrustZone(updatedBank.getTrustZone());

        bankRepository.save(oldBank);

        return true;
    }

    public void deleteBankByAdminId(Long adminId) {
        User admin = userService.findByIdWithDetails(adminId);
        Bank bank = this.findByAdminEmail(admin.getEmail());
        bankRepository.deleteById(bank.getId());
    }
}
