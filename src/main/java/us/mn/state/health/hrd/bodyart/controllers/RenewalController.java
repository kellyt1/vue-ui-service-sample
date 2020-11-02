package us.mn.state.health.hrd.bodyart.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import us.mn.state.health.hrd.bodyart.domain.MakePaymentResponse;
import us.mn.state.health.hrd.bodyart.domain.TechnicianRenewalApplication;
import us.mn.state.health.hrd.bodyart.service.ApplicationService;
import us.mn.state.health.hrd.bodyart.service.RenewalService;

import javax.inject.Inject;

@RestController
@RequestMapping(path = "/renewal", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
public class RenewalController {

    @Inject
    private ApplicationService applicationService;

    @Inject
    private RenewalService renewalService;

    @GetMapping(path = "/token/{token}")
    public TechnicianRenewalApplication getByRenewalToken(@PathVariable String token) {
        return renewalService.createRenewalApplication(token);
    }

    @PostMapping(path = "/")
    public MakePaymentResponse submitRenewal(@RequestBody TechnicianRenewalApplication application) {
        log.info("Renewal Application {}", application);

        MakePaymentResponse response = renewalService.manageRenewalSubmission(application);

        return response;
    }

}
