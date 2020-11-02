package us.mn.state.health.hrd.bodyart.controllers;

import org.apache.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import us.mn.state.health.hrd.bodyart.domain.LicenseClaimRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.License;
import us.mn.state.health.hrd.bodyart.jpa.domain.LicenseCurrent;
import us.mn.state.health.hrd.bodyart.service.LicenseService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/license", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LicenseController {

    @Inject
    LicenseService licenseService;

    @GetMapping(path = "/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LicenseCurrent>> findByUser() {
        return new ResponseEntity(licenseService.findLicensesForUser(), HttpStatus.OK);
    }

    @GetMapping(path = "/subCategory/{cat}")
    public ResponseEntity<List<LicenseCurrent>> findBySubCategory(@PathVariable String cat) {
        return new ResponseEntity(licenseService.findLicensesForSubCategory(cat), HttpStatus.OK);
    }

    @PostMapping(path = "/claim")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity claimLicense(@Valid @RequestBody LicenseClaimRepresentation licClaim, HttpRequest request) {
        License license = licenseService.claimLicense(licClaim);
        if (license == null) {
            return new ResponseEntity("Not Found", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(license.getNumber(), HttpStatus.OK);
        }
    }

}
