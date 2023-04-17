package com.ljselection.exercise.data.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ljselection.exercise.data.entities.RateEntity;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Integer> {

  Optional<RateEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
      Integer productId, Integer brandId, LocalDateTime rateDate1, LocalDateTime rateDate2);

}
