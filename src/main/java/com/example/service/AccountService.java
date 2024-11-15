package com.example.service;

import java.util.Optional;
import java.util.PrimitiveIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;
@Service
public class AccountService {

    private static final int MIN_LENGTH_PASSWORD = 4;
    AccountRepository accountRepository;
    
    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new IllegalArgumentException("error registering account");
        }
        else if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("duplicate username");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Optional<Account> loginAccount = accountRepository.findByUsername(account.getUsername());
        if (loginAccount.isPresent() && loginAccount.get().getPassword().equals(account.getPassword())) {
                return loginAccount.get();
        } else {
            throw new UnauthorizedException("credentials do not match!");
        }
    }

}