package us.mn.state.health.hrd.bodyart.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.mn.state.health.hrd.bodyart.jpa.domain.LicenseCurrent;
import us.mn.state.health.hrd.bodyart.jpa.repository.LicenseCurrentRepository;
import us.mn.state.health.hrd.bodyart.service.LicenseService;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class ExpireLicenseJob {

    @Autowired
    private LicenseCurrentRepository licenseCurrentRepository;

    @Autowired
    private LicenseService licenseService;

    @Scheduled(cron = "0 17 17 * * ?", zone = "America/Chicago")
    public void execute() {
        log.info("The time is now {}", LocalDate.now());
        List<LicenseCurrent> licensesToExpire =
                licenseCurrentRepository.findByExpirationDateLessThan(LocalDate.now());
        log.info("Total number of licenses to expire: {}.", licensesToExpire.size());
        for (LicenseCurrent licenseCurrent : licensesToExpire) {
            licenseService.expireLicense(licenseCurrent.getId());
        }
    }
}
