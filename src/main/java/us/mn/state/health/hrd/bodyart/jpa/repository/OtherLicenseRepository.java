package us.mn.state.health.hrd.bodyart.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.mn.state.health.hrd.bodyart.jpa.domain.OtherLicense;

@Repository
public interface OtherLicenseRepository extends CrudRepository<OtherLicense, String> {

}