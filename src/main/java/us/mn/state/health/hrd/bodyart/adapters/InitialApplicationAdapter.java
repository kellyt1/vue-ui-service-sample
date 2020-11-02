package us.mn.state.health.hrd.bodyart.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import us.mn.state.health.hrd.bodyart.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.domain.Address;
import us.mn.state.health.hrd.bodyart.jpa.repository.QuestionRepository;
import us.mn.state.health.hrd.bodyart.security.AuthenticationFacade;

import javax.inject.Inject;
import java.util.ArrayList;

@Service
@Slf4j
public class InitialApplicationAdapter {

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AuthenticationFacade authenticationFacade;

    public Application toModel(TechnicianApplication representation) {
        return toModel(representation, null);
    }

    public Application toModel(TechnicianApplication representation, Application model) {

        if (model == null){
            model = new Application();

            License license = new License();
            license.setStatus("PENDING");
            license.setUserId(authenticationFacade.getAccessToken().getSubject());
            license.setLicenseCategory(representation.getCategory());
            license.setLicenseSubCategory(representation.getSubCategory());

            //TODO
            //license.setNumber()

            model.setLicense(license);
        }

        model.getLicense().setLicenseType(representation.getLicense().getLicenseType());
        model.getLicense().setSocialSecurityNumber(representation.getLicense().getSocialSecurityNumber());
        model.getLicense().setDateOfBirth(representation.getLicense().getDateOfBirth());

        model.setApplicationType(representation.getApplicationType());
        model.setAdditionalInformation(representation.getAdditionalInformation());
        model.setApplyMethod(representation.getApplyMethod());
        model.setEmailAddress(representation.getEmailAddress());
        model.setFirstName(representation.getFirstName());
        model.setLastName(representation.getLastName());
        model.setMiddleName(representation.getMiddleName());
        model.setPersonalPhone(representation.getPersonalPhone());
        model.setSupervisedEstFullAddress(representation.getSupervisedEstFullAddress());
        model.setSupervisedEstHours(representation.getSupervisedEstHours());
        model.setSupervisedEstName(representation.getSupervisedEstName());
        model.setSupervisedEstPhoneNumber(representation.getSupervisedEstPhoneNumber());
        model.setSupervisedEstWebsite(representation.getSupervisedEstWebsite());
        model.setWorkPhone(representation.getWorkPhone());

        for (SupervisorRepresentation sup: representation.getMnSupervisor()) {
            MnSupervisor supervisor = new MnSupervisor();
            supervisor.setName(sup.getName());
            supervisor.setLicenseNbr(sup.getLicenseNbr());
            supervisor.setApplication(model);
            model.getMnSupervisors().add(supervisor);
        }

        for(NameRepresentation nm: representation.getOtherNames()) {
            OtherNames name = new OtherNames();
            name.setFirstName(nm.getFirstName());
            name.setLastName(nm.getLastName());
            name.setMiddleName(nm.getMiddleName());
            name.setApplication(model);
            model.getOtherNames().add(name);
        }

        for(QuestionResponseRepresentation qr: representation.getQuestionResponses()){
            QuestionResponse response = new QuestionResponse();
            response.setResponse(qr.getResponse().equalsIgnoreCase("Y"));
            response.setAdditionalResponse(qr.getAdditionalResponse());
            response.setQuestion(questionRepository.findById(qr.getQuestion()).orElse(null));
            response.setApplication(model);
            model.getQuestionResponses().add(response);
        }

        for(AddressRepresentation ar: representation.getAddress()){
            Address address = new Address();
            address.setAddressType(ar.getAddressType());
            address.setCity(ar.getCity());
            address.setPostalCode(ar.getPostalCode());
            address.setState(ar.getState());
            address.setStreetAddress(ar.getStreetAddress());
            address.setPreferred(ar.getPreferred());
            address.setApplication(model);
            model.getAddresses().add(address);
        }

        return model;
    }

    public TechnicianApplication toTechnicianApplicationRepresentation(Application model) {
        TechnicianApplication rep = new TechnicianApplication();

        LicenseRepresentation licenseRepresentation = new LicenseRepresentation();
        licenseRepresentation.setApplicatonId(model.getId());
        licenseRepresentation.setDateOfBirth(model.getLicense().getDateOfBirth());
        licenseRepresentation.setEffectiveDate(model.getLicense().getEffectiveDate());
        licenseRepresentation.setExpirationDate(model.getLicense().getEffectiveDate());
        licenseRepresentation.setLicenseId(model.getLicense().getId());
        licenseRepresentation.setLicenseNbr(model.getLicense().getNumber());
        licenseRepresentation.setSocialSecurityNumber(model.getLicense().getSocialSecurityNumber());
        licenseRepresentation.setStatus(model.getLicense().getStatus());
        licenseRepresentation.setLicenseType(model.getLicense().getLicenseType());
        rep.setLicense(licenseRepresentation);

        rep.setApplicationType(model.getApplicationType());
        rep.setAdditionalInformation(model.getAdditionalInformation());
        rep.setApplyMethod(model.getApplyMethod());
        rep.setEmailAddress(model.getEmailAddress());
        rep.setFirstName(model.getFirstName());
        rep.setLastName(model.getLastName());
        rep.setMiddleName(model.getMiddleName());
        rep.setPersonalPhone(model.getPersonalPhone());
        rep.setSupervisedEstFullAddress(model.getSupervisedEstFullAddress());
        rep.setSupervisedEstHours(model.getSupervisedEstHours());
        rep.setSupervisedEstName(model.getSupervisedEstName());
        rep.setSupervisedEstPhoneNumber(model.getSupervisedEstPhoneNumber());
        rep.setSupervisedEstWebsite(model.getSupervisedEstWebsite());
        rep.setWorkPhone(model.getWorkPhone());

        for (MnSupervisor sup: model.getMnSupervisors()) {
            if (rep.getMnSupervisor() == null) {
                rep.setMnSupervisor(new ArrayList<>());
            }
            SupervisorRepresentation supervisor = new SupervisorRepresentation();
            supervisor.setName(sup.getName());
            supervisor.setLicenseNbr(sup.getLicenseNbr());
            supervisor.setApplicationId(model.getId());
            supervisor.setId(sup.getId());
            rep.getMnSupervisor().add(supervisor);
        }

        for(OtherNames nm: model.getOtherNames()) {
            if (rep.getOtherNames() == null) {
                rep.setOtherNames(new ArrayList<>());
            }
            NameRepresentation name = new NameRepresentation();
            name.setFirstName(nm.getFirstName());
            name.setLastName(nm.getLastName());
            name.setMiddleName(nm.getMiddleName());
            name.setApplicationId(model.getId());
            name.setId(nm.getId());
            rep.getOtherNames().add(name);
        }

        for(QuestionResponse qr: model.getQuestionResponses()){
            if (rep.getQuestionResponses() == null) {
                rep.setQuestionResponses(new ArrayList<>());
            }
            QuestionResponseRepresentation response = new QuestionResponseRepresentation();
            response.setId(qr.getId());
            response.setApplicationId(model.getId());
            response.setQuestion(qr.getQuestion().getCode());
            response.setAdditionalResponse(qr.getAdditionalResponse());
            rep.getQuestionResponses().add(response);
        }

        for (Address address: model.getAddresses()) {
            if (rep.getAddress() == null) {
                rep.setAddress(new ArrayList<>());
            }
            AddressRepresentation addr = new AddressRepresentation();
            addr.setPostalCode(address.getPostalCode());
            addr.setState(address.getState());
            addr.setStreetAddress(address.getStreetAddress());
            addr.setCity(address.getCity());
            addr.setAddressType(address.getAddressType());
        }

        return rep;
    }

}
