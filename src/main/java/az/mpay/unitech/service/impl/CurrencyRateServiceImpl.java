package az.mpay.unitech.service.impl;

import az.mpay.unitech.constant.enums.Currency;
import az.mpay.unitech.model.dto.server.CurrencyRateDto;
import az.mpay.unitech.service.MockCurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static az.mpay.unitech.constant.enums.Currency.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyRateServiceImpl implements MockCurrencyRateService {

    @Override
    public CurrencyRateDto getCurrencyRate(Currency from, Currency to) {

        CurrencyRateDto currencyRate = CurrencyRateDto.builder().from(from).to(to).build();

        if (from == USD && to == AZN) {
            currencyRate = currencyRate.toBuilder().rate(new BigDecimal("1.7")).build();
        } else if (from == AZN && to == USD) {
            currencyRate = currencyRate.toBuilder().rate(new BigDecimal("0.59")).build();
        } else if (from == AZN && to == TL) {
            currencyRate = currencyRate.toBuilder().rate(new BigDecimal("17.5")).build();
        } else if (from == TL && to == AZN) {
            currencyRate = currencyRate.toBuilder().rate(new BigDecimal("0.057")).build();
        } else {
            currencyRate = null;
        }

        return currencyRate;
    }
}
