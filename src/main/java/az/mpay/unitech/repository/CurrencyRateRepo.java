package az.mpay.unitech.repository;

import az.mpay.unitech.constant.enums.Currency;
import az.mpay.unitech.model.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepo extends JpaRepository<CurrencyRate, Long> {

    Optional<CurrencyRate> findByFromAndTo(Currency from, Currency to);
}
