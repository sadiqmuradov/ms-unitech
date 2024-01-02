package az.mpay.unitech.service.impl;

import az.mpay.unitech.exception.*;
import az.mpay.unitech.model.entity.*;
import az.mpay.unitech.repository.*;
import az.mpay.unitech.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.mpay.unitech.constant.enums.Currency.created;
import static az.mpay.unitech.constant.enums.Currency.declined;
import static az.mpay.unitech.constant.enums.Currency.exception;
import static az.mpay.unitech.constant.enums.Currency.processed;
import static az.mpay.unitech.constant.enums.Currency.processing;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final TokenRepo tokenRepo;
    private final UserRepo userRepo;

    @Override
    public Long checkUser(String token) {

        Optional<Token> tokenOpt = tokenRepo.findByToken(token);

        if (tokenOpt.isEmpty()) {
            throw new TokenNotFoundException("Authorization token is invalid");
        }

        Optional<User> userOpt = userRepo.findByPin(tokenOpt.get().getPin());

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User with pin '%s' not found", tokenOpt.get().getPin());
        }

        return userOpt.get().getId();
    }
}
