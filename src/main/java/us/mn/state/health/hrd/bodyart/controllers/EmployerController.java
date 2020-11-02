package us.mn.state.health.hrd.bodyart.controllers;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.mn.state.health.hrd.bodyart.domain.EmployerRepresentation;
import us.mn.state.health.hrd.bodyart.service.EmployerService;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(path = "/Employer", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class EmployerController {

    @Inject
    EmployerService employerService;

    @GetMapping(path = "/public/{search}")
    public List<EmployerRepresentation> searchEmployerName(@PathVariable String search) {
        return employerService.findAllByNameContaining(search);
    }
}
