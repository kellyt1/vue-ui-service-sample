package us.mn.state.health.hrd.bodyart.service;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.Constants;
import us.mn.state.health.hrd.bodyart.domain.LicenseClaimRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.License;
import us.mn.state.health.hrd.bodyart.jpa.domain.LicenseCurrent;
import us.mn.state.health.hrd.bodyart.jpa.repository.LicenseCurrentRepository;
import us.mn.state.health.hrd.bodyart.jpa.repository.LicenseRepository;
import us.mn.state.health.hrd.bodyart.security.AuthenticationFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LicenseService {

    @Inject
    LicenseRepository licenseRepository;

    @Inject
    private LicenseCurrentRepository licenseCurrentRepository;

    @Inject
    private AuthenticationFacade authenticationFacade;

    /*
        This method returns all the licenses for the user that is currently logged in.
     */
    public List<LicenseCurrent> findLicensesForUser() {
        AccessToken accessToken = authenticationFacade.getAccessToken();
        return licenseCurrentRepository.findByUserId(accessToken.getSubject());
    }

    public List<LicenseCurrent> findLicensesForSubCategory(String subCategory) {
        AccessToken accessToken = authenticationFacade.getAccessToken();
        return licenseCurrentRepository.findByLicenseSubCategory(subCategory);
    }

    public void expireLicense(String licenseId) {
        Optional<License> oLicense = licenseRepository.findById(licenseId);
        License license = null;
        if (oLicense.isPresent()) {
            license = oLicense.get();
            license.setStatus(Constants.LICENSE_EXPIRED);
            licenseRepository.save(license);
            log.info("Expired License {} - {}", license.getNumber(), license.getId());
        } else {
            log.error("Could not locate License {} to expire.", license.getId());
        }
    }

    public License claimLicense(LicenseClaimRepresentation licClaim) {
        License lic = licenseRepository.findByNumberAndSocialSecurityNumberAndUserIdIsNull(licClaim.getLicNbr(), licClaim.getSsn());
        if (lic != null) {
            AccessToken accessToken = authenticationFacade.getAccessToken();
            lic.setUserId(accessToken.getSubject());
            licenseRepository.save(lic);
        }
        return lic;
    }
}
