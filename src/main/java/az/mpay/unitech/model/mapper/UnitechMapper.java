package az.mpay.unitech.model.mapper;

import az.mpay.unitech.model.dto.server.AccountDto;
import az.mpay.unitech.model.dto.server.CurrencyRateDto;
import az.mpay.unitech.model.dto.server.TransferDto;
import az.mpay.unitech.model.dto.server.UserDto;
import az.mpay.unitech.model.entity.Account;
import az.mpay.unitech.model.entity.CurrencyRate;
import az.mpay.unitech.model.entity.Transfer;
import az.mpay.unitech.model.entity.User;
import az.mpay.unitech.model.request.server.TransferRequest;
import az.mpay.unitech.model.request.server.UserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UnitechMapper {

    User fromRequest(UserRequest request);

    UserDto toDto(User user);

    AccountDto toDto(Account account);

    List<AccountDto> toDtoList(List<Account> accounts);

    Transfer fromRequest(TransferRequest request);

    TransferDto toDto(Transfer transfer);

    CurrencyRate fromDto(CurrencyRateDto dto);

    CurrencyRateDto toDto(CurrencyRate currencyRate);
}
