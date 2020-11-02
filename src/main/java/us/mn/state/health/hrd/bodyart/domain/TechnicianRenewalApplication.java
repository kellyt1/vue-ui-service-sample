package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import us.mn.state.health.hrd.bodyart.utils.BooleanDeSer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TechnicianRenewalApplication {

    public TechnicianRenewalApplication() {
        this.continuingEducations = new ArrayList<>();
        this.otherLicenses = new ArrayList<>();
    }

    private String renewalId;
    private String licenseNbr;
    private LicenseType licenseType;

    private ApplicationType applicationType = ApplicationType.RENEWAL;

    private String lastName;
    private String firstName;
    private String middleName;

    private AddressRepresentation businessAddress;  //data model
    private List<AddressRepresentation> homeAddress;      //data model

    private String personalPhone;
    private String workPhone;
    private String mobilePhone;   //data model

    private String emailAddress;

    // establishment where you work - what does this map to on the initial application.
    // drop-down?  free-form text?

    private List<AttachmentRepresentation> continuingEducations; // data model

    @JsonSerialize(using= BooleanDeSer.YesNoBooleanSerializer.class)
    @JsonDeserialize(using= BooleanDeSer.YesNoBooleanDeserializer.class)
    private Boolean issuedOtherLicense;  // data model
    private List<OtherLicenseRepresentation> otherLicenses; // data model

    @JsonSerialize(using= BooleanDeSer.YesNoBooleanSerializer.class)
    @JsonDeserialize(using= BooleanDeSer.YesNoBooleanDeserializer.class)
    private Boolean adverseActionTaken;  // data model
    private String adverseActionText;    // data model

    @JsonSerialize(using= BooleanDeSer.YesNoBooleanSerializer.class)
    @JsonDeserialize(using= BooleanDeSer.YesNoBooleanDeserializer.class)
    private Boolean conviction; // data model
    private String convictionText;  // data model

    private String additionalInformation;

    private BigDecimal renewalFee;
    private BigDecimal lateFee;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate lateFeeEffectiveDate;
}
