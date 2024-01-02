package az.mpay.unitech.model.mapper;

import az.mpay.unitech.model.dto.client.UberDriverProfileDto;
import az.mpay.unitech.model.dto.server.AccountDto;
import az.mpay.unitech.model.dto.server.DriverProfileDto;
import az.mpay.unitech.model.dto.server.UserDto;
import az.mpay.unitech.model.entity.Account;
import az.mpay.unitech.model.entity.DriverProfile;
import az.mpay.unitech.model.entity.ProfileScheduleRun;
import az.mpay.unitech.model.entity.User;
import az.mpay.unitech.model.request.server.UserRequest;
import az.mpay.unitech.model.response.client.DriverProfilesResp;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@DecoratedWith(UnitechMapperDecorator.class)
public interface UnitechMapper {

    User fromRequest(UserRequest request);

    UserDto toDto(User user);

    AccountDto toDto(Account account);

    List<AccountDto> toDtoList(List<Account> accounts);

    ProfileScheduleRun fromClientResp(DriverProfilesResp profilesResp);

    @Mappings({
            @Mapping(expression = "java(dto.getAccounts().get(0).getBalance())", target = "balance"),
            @Mapping(expression = "java(dto.getAccounts().get(0).getBalanceLimit())", target = "balanceLimit"),
            @Mapping(expression = "java(dto.getAccounts().get(0).getCurrency())", target = "currency"),
            @Mapping(expression = "java(dto.getAccounts().get(0).getLastTransactionDate())",
                     target = "lastTransactionDate"),
            @Mapping(expression = "java(dto.getAccounts().get(0).getType())", target = "type"),
            @Mapping(source = "profile.id", target = "id"),
            @Mapping(source = "profile.firstName", target = "firstName"),
            @Mapping(source = "profile.lastName", target = "lastName"),
            @Mapping(source = "profile.middleName", target = "middleName"),
            @Mapping(source = "profile.phones", target = "phones"),
            @Mapping(source = "profile.driverLicense.country", target = "driverLicenseCountry"),
            @Mapping(source = "profile.driverLicense.expirationDate", target = "driverLicenseExpirationDate"),
            @Mapping(source = "profile.driverLicense.issueDate", target = "driverLicenseIssueDate"),
            @Mapping(source = "profile.driverLicense.normalizedNumber", target = "driverLicenseNormalizedNumber"),
            @Mapping(source = "profile.driverLicense.number", target = "driverLicenseNumber"),
            @Mapping(source = "profile.workStatus", target = "workStatus"),
            @Mapping(source = "profile.hireDate", target = "hireDate"),
            @Mapping(source = "profile.fireDate", target = "fireDate"),
            @Mapping(source = "profile.createdDate", target = "createdDate"),
            @Mapping(source = "profile.modifiedDate", target = "modifiedDate")
    })
    DriverProfile fromClientDto(UberDriverProfileDto dto);

    @Mapping(source = "phones", target = "phoneNumbers")
    DriverProfileDto toDto(DriverProfile profile);
}
