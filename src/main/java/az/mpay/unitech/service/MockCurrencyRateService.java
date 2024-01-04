package az.mpay.unitech.service;

import az.mpay.unitech.constant.enums.Currency;
import az.mpay.unitech.model.dto.server.CurrencyRateDto;

public interface MockCurrencyRateService {

    CurrencyRateDto getCurrencyRate(Currency from, Currency to);
}
