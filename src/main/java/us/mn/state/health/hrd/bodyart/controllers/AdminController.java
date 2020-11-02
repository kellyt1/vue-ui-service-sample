package us.mn.state.health.hrd.bodyart.controllers;

import org.apache.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import us.mn.state.health.hrd.bodyart.domain.Settings;
import us.mn.state.health.hrd.bodyart.jpa.domain.ApplicationProperty;
import us.mn.state.health.hrd.bodyart.jpa.repository.ApplicationPropertyRepository;
import us.mn.state.health.hrd.bodyart.service.ApplicationPropertyService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AdminController {

    @Inject
    private ApplicationPropertyRepository applicationPropertyRepository;

    @Inject
    private ApplicationPropertyService applicationPropertyService;

    @PostMapping("/settings/query")
    public Settings search(@RequestBody List<String> query, HttpRequest request) {
        Map<String, String> results = applicationPropertyService.findByIds(query);
        Settings settings = new Settings();
        settings.setSettings(results);
        return settings;
    }

    @PutMapping("/settings")
    public void save(@RequestBody Map<String, String> payload, HttpRequest request) {
        applicationPropertyService.save(payload);
    }

}
