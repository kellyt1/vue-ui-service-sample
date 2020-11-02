package us.mn.state.health.hrd.bodyart.controllers;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.mn.state.health.hrd.bodyart.jpa.domain.ApplicationProperty;
import us.mn.state.health.hrd.bodyart.jpa.repository.ApplicationPropertyRepository;

import javax.inject.Inject;

@RestController
@RequestMapping(path = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SettingsController {

    @Inject
    private ApplicationPropertyRepository applicationPropertyRepository;

    @GetMapping("/welcome")
    public ApplicationProperty getWelcomeText() {
        return applicationPropertyRepository.findById("welcome_text")
                .orElse(new ApplicationProperty("welcome_text"));
    }
}