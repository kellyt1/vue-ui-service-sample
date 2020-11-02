package us.mn.state.health.hrd.bodyart.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.AddressRepresentation;
import us.mn.state.health.hrd.bodyart.domain.OtherLicenseRepresentation;
import us.mn.state.health.hrd.bodyart.domain.TechnicianRenewalApplication;
import us.mn.state.health.hrd.bodyart.jpa.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.repository.LicenseRepository;

import java.time.LocalDate;


@Service
@Slf4j
public class RenewalApplicationAdapter {

    @Autowired
    private LicenseRepository licenseRepository;

    public Application toModel(TechnicianRenewalApplication representation) {
        return toModel(representation, null);
    }

    public Application toModel(TechnicianRenewalApplication representation, Application model) {
        if (model == null){
            model = new Application();
        }

        License license = licenseRepository.findByNumber(representation.getLicenseNbr());
        license.setPendingApplication(Boolean.TRUE);

        model.setLicense(license);
        model.setApplicationType(representation.getApplicationType());
        model.setAdditionalInformation(representation.getAdditionalInformation());
        log.error(representation.getLastName());

        model.setEmailAddress(representation.getEmailAddress());
        model.setFirstName(representation.getFirstName());
        model.setLastName(representation.getLastName());
        model.setMiddleName(representation.getMiddleName());
        model.setPersonalPhone(representation.getPersonalPhone());

        model.setWorkPhone(representation.getWorkPhone());
        model.setMobilePhone(representation.getMobilePhone());

        model.setConviction(representation.getConviction());
        model.setConvictionText(representation.getConvictionText());
        model.setAdverseActionTaken(representation.getAdverseActionTaken());
        model.setAdverseActionText(representation.getAdverseActionText());

        model.setReceivedDate(LocalDate.now());

        model.setAdditionalInformation(representation.getAdditionalInformation());

        for (AddressRepresentation addr : representation.getHomeAddress()) {
            Address home = new Address();
            home.setAddressType(addr.getAddressType());
            home.setCity(addr.getCity());
            home.setPostalCode(addr.getPostalCode());
            home.setState(addr.getState());
            home.setStreetAddress(addr.getStreetAddress());
            home.setPreferred(addr.getPreferred());
            home.setApplication(model);
            model.getAddresses().add(home);
        }

        for (OtherLicenseRepresentation olr : representation.getOtherLicenses()) {
            OtherLicense ol = new OtherLicense();
            ol.setApplication(model);
            ol.setIssueDate(olr.getDateIssued());
            ol.setLicenseNumber(olr.getLicenseId());
            ol.setState(olr.getState());
            ol.setStatus(olr.getStatus());
            model.getOtherLicenses().add(ol);
        }

        Payment renewalFee = new Payment();
        renewalFee.setDate(LocalDate.now());
        renewalFee.setAmount(representation.getRenewalFee());
        renewalFee.setStatus("PENDING");
        renewalFee.setApplication(model);
        renewalFee.setFeeType("Renewal Fee");
        model.getPayments().add(renewalFee);

        if (LocalDate.now().isAfter(representation.getLateFeeEffectiveDate())) {
            Payment lateFee = new Payment();
            lateFee.setDate(LocalDate.now());
            lateFee.setAmount(representation.getLateFee());
            lateFee.setStatus("PENDING");
            lateFee.setApplication(model);
            lateFee.setFeeType("Late Fee");
            model.getPayments().add(lateFee);
        }

        return model;
    }
}
