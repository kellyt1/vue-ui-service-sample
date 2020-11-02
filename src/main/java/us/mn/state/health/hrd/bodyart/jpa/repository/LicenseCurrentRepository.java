package us.mn.state.health.hrd.bodyart.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.mn.state.health.hrd.bodyart.jpa.domain.LicenseCurrent;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LicenseCurrentRepository extends CrudRepository<LicenseCurrent, String> {

    LicenseCurrent findByRenewalToken(String renewalToken);

    List<LicenseCurrent> findByUserId(String userId);

    List<LicenseCurrent> findByExpirationDateLessThan(LocalDate cutoff);

    List<LicenseCurrent> findByLicenseSubCategory(String subCategory);
}