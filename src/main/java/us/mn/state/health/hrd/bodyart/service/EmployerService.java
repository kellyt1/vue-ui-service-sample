package us.mn.state.health.hrd.bodyart.service;

import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.EmployerRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Employer;
import us.mn.state.health.hrd.bodyart.jpa.repository.EmployerRepository;
import us.mn.state.health.hrd.bodyart.mappers.EmployerMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {
    @Inject
    EmployerRepository employerRepository;

    @Inject
    EmployerMapper employerMapper;

    public EmployerRepresentation getById(String id) {
        Employer employer =  employerRepository.findById(id).orElse(null);
        return employer != null ? employerMapper.fromDatabase(employer) : null;
    }

    public Employer save(EmployerRepresentation e) {
        Employer model = employerMapper.toDatabase(e);
        employerRepository.save(model);
        return model;
    }

    public String update(EmployerRepresentation e) {
        return null;
    }

    public List<EmployerRepresentation> findAllByNameContaining(String search) {
        List<Employer> employers = employerRepository.findAllByNameContainingIgnoreCase(search);
        return employers != null ? employers.stream().map(e -> employerMapper.fromDatabase(e)).collect(Collectors.toList()): null;
    }

}
