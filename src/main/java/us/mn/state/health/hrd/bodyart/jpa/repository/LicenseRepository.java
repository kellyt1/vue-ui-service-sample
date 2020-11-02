package us.mn.state.health.hrd.bodyart.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.mn.state.health.hrd.bodyart.jpa.domain.License;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    License findByNumber(String number);

    License findByNumberAndSocialSecurityNumberAndUserIdIsNull(String number, String socialSecurityNumber);
}