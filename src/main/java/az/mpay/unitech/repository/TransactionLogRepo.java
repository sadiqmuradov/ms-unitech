package az.mpay.unitech.repository;

import az.mpay.unitech.constant.enums.Currency;
import az.mpay.unitech.model.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

public interface TransactionLogRepo extends JpaRepository<TransactionLog, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE TransactionLog t
            SET t.status = CASE WHEN (SELECT COUNT(t2)
                                      FROM TransactionLog t2
                                      WHERE t2.phoneNumber = :phoneNumber
                                        AND t2.status != :status
                                        AND t2.updatedAt >= :lowerBound) > 1 THEN :declined
                                ELSE :processing
                           END
            WHERE t.id = :id
           """)
    int updateStatus(@Param("phoneNumber") String phoneNumber,
                     @Param("status") Currency status,
                     @Param("lowerBound") OffsetDateTime lowerBound,
                     @Param("declined") Currency declined,
                     @Param("processing") Currency processing,
                     @Param("id") Long id);
}
