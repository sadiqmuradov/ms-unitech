package az.mpay.unitech.exception.handler;

import az.mpay.unitech.exception.ApiException;
import az.mpay.unitech.exception.CoinNotFoundException;
import az.mpay.unitech.exception.InsufficientBalanceException;
import az.mpay.unitech.exception.MultipleAccountsException;
import az.mpay.unitech.exception.UserAlreadyExistsException;
import az.mpay.unitech.exception.ProfileNotFoundException;
import az.mpay.unitech.exception.TransactionDeclinedException;
import az.mpay.unitech.exception.TransactionLogNotFoundException;
import az.mpay.unitech.exception.UnauthorizedException;
import az.mpay.unitech.exception.UserNotFoundException;
import az.mpay.unitech.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            CoinNotFoundException.class,
            ProfileNotFoundException.class,
            UnsupportedOperationException.class
    })
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException exception) {
       ApiException apiException = ApiException.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .code(HttpStatus.NOT_FOUND.value())
                            .time(OffsetDateTime.now())
                            .build();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    public ResponseEntity<Object> handleConflictExceptions(RuntimeException exception) {
        ApiException apiException = ApiException.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .code(HttpStatus.CONFLICT.value())
                .time(OffsetDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            ValidationException.class,
            InsufficientBalanceException.class,
            TransactionDeclinedException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Object> handleBadRequestExceptions(Exception exception) {
        ApiException apiException = ApiException.builder()
                .message(exception.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .time(OffsetDateTime.now())
                .build();

        if (exception instanceof MethodArgumentNotValidException ex) {
            List<String> errors = new ArrayList<>(ex.getBindingResult()
                                                    .getFieldErrors()
                                                    .stream()
                                                    .map(e -> e.getField() + " " + e.getDefaultMessage())
                                                    .filter(StringUtils::isNotBlank)
                                                    .toList());

            errors.addAll(ex.getBindingResult()
                            .getGlobalErrors()
                            .stream()
                            .map(e -> e.getObjectName() + " " + e.getDefaultMessage())
                            .filter(StringUtils::isNotBlank)
                            .toList());

            apiException = apiException.toBuilder()
                                       .message("processing failed")
                                       .errors(errors)
                                       .build();
        }

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ UnauthorizedException.class })
    public ResponseEntity<Object> handleUnauthorizedException(RuntimeException exception) {
        ApiException apiException = ApiException.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .code(HttpStatus.UNAUTHORIZED.value())
                .time(OffsetDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            TransactionLogNotFoundException.class,
            Exception.class
    })
    public ResponseEntity<Object> handleInternalExceptions(Exception exception) {
        ApiException apiException = ApiException.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .time(OffsetDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
