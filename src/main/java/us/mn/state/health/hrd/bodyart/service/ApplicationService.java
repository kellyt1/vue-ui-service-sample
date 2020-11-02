package us.mn.state.health.hrd.bodyart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.adapters.InitialApplicationAdapter;
import us.mn.state.health.hrd.bodyart.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.repository.ApplicationRepository;
import us.mn.state.health.hrd.bodyart.payment.RhapsodyService;
import us.mn.state.health.hrd.bodyart.payment.domain.Fee;
import us.mn.state.health.hrd.bodyart.payment.domain.Receipt;
import us.mn.state.health.hrd.bodyart.payment.domain.usbank.MakePayment;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicationService {
    @Inject
    private ApplicationRepository applicationRepository;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private EmployerService employerService;

    @Inject
    private LicenseService licenseService;

    @Inject
    private InitialApplicationAdapter initialApplicationAdapter;

    @Inject
    private RhapsodyService rhapsodyService;

    public TechnicianApplication getById(String id) {
        Application application =  applicationRepository.findById(id).orElse(null);
        return application != null ?
            initialApplicationAdapter.toTechnicianApplicationRepresentation(application) : null;
    }

    public Application save(TechnicianApplication ta) {
        Application model = initialApplicationAdapter.toModel(ta);

        model.setPayments(new ArrayList<>());
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(420.00));
        payment.setApplication(model);
        payment.setDate(LocalDate.now());
        payment.setStatus("PENDING");
        payment.setFeeType("Initial Application");
        payment.setPaymentType(PaymentType.CC);
        model.getPayments().add(payment);

        applicationRepository.save(model);

        List<Attachment> attachments = attachmentService.updateApplicationAssign(ta.getAttachments(), model);
        model.getAttachments().addAll(attachments);

        for (EmployerRepresentation e: ta.getEmployers()) {
            e.setApplicatonId(model.getId());
            Employer emp = employerService.save(e);
            model.getEmployers().add(emp);
        }
        return model;
    }

    public MakePaymentResponse manageApplicationSubmission(TechnicianApplication application) {
        Application appl = save(application);
        Receipt receipt = createReceipt(appl);
        MakePayment makePayment = rhapsodyService.buildMakePaymentPayload(receipt); //TODO
        String response = rhapsodyService.submitMakePayment(makePayment);

        ObjectMapper objectMapper = new ObjectMapper();
        MakePaymentResponse makePaymentResponse = new MakePaymentResponse();
        try {
            makePaymentResponse = objectMapper.readValue(response, MakePaymentResponse.class);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        System.out.println(makePaymentResponse);
        return makePaymentResponse;
    }

    public TechnicianApplication update(TechnicianApplication ta) {
        Optional<Application> app = applicationRepository.findById(ta.getApplicationId());
        if (app.isPresent()){
            Application model = app.get();
            model.setApplicationType(ta.getApplicationType());
            model.setApplyMethod(ta.getApplyMethod());
            model.setFirstName(ta.getFirstName());
            model.setLastName(ta.getLastName());
            model.setMiddleName(ta.getMiddleName());
            for (AddressRepresentation addr: ta.getAddress()) {
                Address address = new Address();
                address.setStreetAddress(addr.getStreetAddress());
                address.setCity(addr.getCity());
                address.setState(addr.getState());
                address.setPostalCode(addr.getPostalCode());
                address.setAddressType(addr.getAddressType());
                model.getAddresses().add(address);
            }
            model.setPersonalPhone(ta.getPersonalPhone());
            model.setWorkPhone(ta.getWorkPhone());
            model.setEmailAddress(ta.getEmailAddress());
            model.setSupervisedEstName(ta.getSupervisedEstName());
            model.setSupervisedEstFullAddress(ta.getSupervisedEstFullAddress());
            model.setSupervisedEstPhoneNumber(ta.getSupervisedEstPhoneNumber());
            model.setSupervisedEstWebsite(ta.getSupervisedEstWebsite());
            model.setSupervisedEstHours(ta.getSupervisedEstHours());
            model.setAdditionalInformation(ta.getAdditionalInformation());

            model.getLicense().setLicenseType(ta.getLicense().getLicenseType());

            applicationRepository.save(model);
            return initialApplicationAdapter.toTechnicianApplicationRepresentation(model);
        }
        return null;
    }

    public Receipt createReceipt(Application appl) {
        Receipt rcpt = new Receipt();
        Address addr = appl.getPreferredAddress();
        rcpt.setAddressTxt1(addr.getStreetAddress());
        rcpt.setCity(addr.getCity());
        rcpt.setDate(appl.getReceivedDate());
        rcpt.setEmailAddress(appl.getEmailAddress());
        rcpt.setFirstName(appl.getFirstName());
        rcpt.setLastName(appl.getLastName());
        rcpt.setStateCode(appl.getPersonalPhone());
        rcpt.setZipCode(addr.getPostalCode()+""); //TODO - should we put varchar in the db?

        Long tx = UUID.fromString(appl.getId()).getMostSignificantBits() & Long.MAX_VALUE;
        rcpt.setTransactionNbr(tx);

        for (Payment payment : appl.getPayments()) {
            Fee fee = new Fee();
            fee.setAmount(payment.getAmount());
            fee.setDescription(payment.getFeeType());
            fee.setQuantity(1);
            fee.setSpeedChart("H12WMIW1"); //TODO
            rcpt.addFee(fee);
        }
        return rcpt;
    }

}
