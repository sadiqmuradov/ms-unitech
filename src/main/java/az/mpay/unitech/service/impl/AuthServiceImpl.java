package az.mpay.unitech.service.impl;

import az.mpay.unitech.exception.UnauthorizedException;
import az.mpay.unitech.exception.UserNotFoundException;
import az.mpay.unitech.model.entity.Token;
import az.mpay.unitech.model.entity.User;
import az.mpay.unitech.repository.TokenRepo;
import az.mpay.unitech.repository.UserRepo;
import az.mpay.unitech.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final TokenRepo tokenRepo;
    private final UserRepo userRepo;

    @Override
    public Long checkUser(String token) {

        Optional<Token> tokenOpt = tokenRepo.findByTokenAndExpiredAtAfter(token, OffsetDateTime.now());

        if (tokenOpt.isEmpty()) {
            throw new UnauthorizedException("Authorization failed");
        }

        Optional<User> userOpt = userRepo.findByPin(tokenOpt.get().getPin());

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User with pin '%s' not found", tokenOpt.get().getPin());
        }

        return userOpt.get().getId();
    }
}
