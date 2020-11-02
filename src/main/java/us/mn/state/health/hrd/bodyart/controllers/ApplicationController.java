package us.mn.state.health.hrd.bodyart.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import us.mn.state.health.hrd.bodyart.domain.MakePaymentResponse;
import us.mn.state.health.hrd.bodyart.domain.TechnicianApplication;
import us.mn.state.health.hrd.bodyart.service.ApplicationService;

import javax.inject.Inject;

@RestController
@RequestMapping(path = "/application", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ApplicationController {

    @Inject
    ApplicationService applicationService;

    @GetMapping(path = "/{id}")
    public TechnicianApplication getById(@PathVariable String id) {
        return applicationService.getById(id);
    }

    @PostMapping(path = "/{id}")
    public TechnicianApplication updateApplication(@PathVariable String id, @RequestBody TechnicianApplication app) {
        TechnicianApplication result = applicationService.update(app);
        return result;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public MakePaymentResponse createApplication(@RequestBody TechnicianApplication app) {
        MakePaymentResponse result = applicationService.manageApplicationSubmission(app);
        return result;
    }
}
