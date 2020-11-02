package us.mn.state.health.hrd.bodyart.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.mn.state.health.hrd.bodyart.jpa.domain.Employer;

import java.util.List;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, String> {

    public List<Employer> findAllByNameContainingIgnoreCase(String search);
}