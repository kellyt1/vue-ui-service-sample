package us.mn.state.health.hrd.bodyart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.adapters.RenewalApplicationAdapter;
import us.mn.state.health.hrd.bodyart.domain.*;
import us.mn.state.health.hrd.bodyart.jpa.domain.Address;
import us.mn.state.health.hrd.bodyart.jpa.domain.Application;
import us.mn.state.health.hrd.bodyart.jpa.domain.LicenseCurrent;
import us.mn.state.health.hrd.bodyart.jpa.domain.Payment;
import us.mn.state.health.hrd.bodyart.jpa.repository.ApplicationRepository;
import us.mn.state.health.hrd.bodyart.jpa.repository.LicenseCurrentRepository;
import us.mn.state.health.hrd.bodyart.payment.RhapsodyService;
import us.mn.state.health.hrd.bodyart.payment.domain.Fee;
import us.mn.state.health.hrd.bodyart.payment.domain.Receipt;
import us.mn.state.health.hrd.bodyart.payment.domain.usbank.MakePayment;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RenewalService {

    @Inject
    private ApplicationRepository applicationRepository;

    @Inject
    private LicenseService licenseService;

    @Inject
    private LicenseCurrentRepository licenseCurrentRepository;

    @Inject
    private RenewalApplicationAdapter renewalApplicationAdapter;

    @Inject
    private RhapsodyService rhapsodyService;

    public TechnicianRenewalApplication createRenewalApplication(String token) {
        //todo - decrypt the JWT token just using a plain text one now for testing

        Optional<LicenseCurrent> oLicenseCurrent = licenseCurrentRepository.findById(token);
        if (oLicenseCurrent.isPresent()) {
            LicenseCurrent licenseCurrent = oLicenseCurrent.get();
            TechnicianRenewalApplication tra = new TechnicianRenewalApplication();
            tra.setFirstName(licenseCurrent.getFirstName());
            tra.setLastName(licenseCurrent.getLastName());
            tra.setMiddleName(licenseCurrent.getMiddleName());
            tra.setLicenseNbr(licenseCurrent.getNumber());
            AddressRepresentation addressRepresentation = new AddressRepresentation();
            addressRepresentation.setAddressType(licenseCurrent.getAddressType());
            addressRepresentation.setStreetAddress(licenseCurrent.getStreetAddress());
            addressRepresentation.setCity(licenseCurrent.getCity());
            addressRepresentation.setState(licenseCurrent.getState());
            addressRepresentation.setPostalCode(licenseCurrent.getPostalCode());
            addressRepresentation.setPreferred(licenseCurrent.getPreferred());
            addressRepresentation.setId(licenseCurrent.getId());
            tra.setHomeAddress(new ArrayList<>());
            tra.getHomeAddress().add(addressRepresentation);
            tra.setEmailAddress(licenseCurrent.getEmailAddress());
            tra.setPersonalPhone(licenseCurrent.getPersonalPhone());
            tra.setWorkPhone(licenseCurrent.getWorkPhone());
            tra.setMobilePhone(licenseCurrent.getMobilePhone());

            tra.setRenewalFee(BigDecimal.valueOf(420));
            tra.setLateFee(BigDecimal.valueOf(150));
            tra.setLateFeeEffectiveDate(licenseCurrent.getExpirationDate());
            return tra;
        } else {
            return null;
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public MakePaymentResponse manageRenewalSubmission(TechnicianRenewalApplication application) {
        // xx save the data to the application, payment, other license table
        // set the status to pending
        // update the attachments that were updated
        // generate a payment token
        // remove the token from the license

        Application appl = applicationRepository.save(renewalApplicationAdapter.toModel(application));
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

    public Receipt createReceipt(Application appl) {
        Receipt rcpt = new Receipt();
        Address preferred = appl.getAddresses().stream().filter(a -> a.getPreferred() == true).collect(Collectors.toList()).get(0);
        rcpt.setAddressTxt1(preferred.getStreetAddress());
        rcpt.setCity(preferred.getCity());
        rcpt.setDate(appl.getReceivedDate());
        rcpt.setEmailAddress(appl.getEmailAddress());
        rcpt.setFirstName(appl.getFirstName());
        rcpt.setLastName(appl.getLastName());
        rcpt.setStateCode(appl.getPersonalPhone());
        rcpt.setZipCode(preferred.getPostalCode()+""); //TODO - should we put varchar in the db?

        Long tx = UUID.fromString(appl.getId()).getMostSignificantBits() & Long.MAX_VALUE;
        rcpt.setTransactionNbr(tx);

        for (Payment payment : appl.getPayments()) {
            Fee fee = new Fee();
            fee.setAmount(payment.getAmount());
            fee.setDescription(payment.getFeeType());
            fee.setQuantity(1);
            fee.setSpeedChart("H12WMIW1");
            rcpt.addFee(fee);
        }
        return rcpt;
    }


}
