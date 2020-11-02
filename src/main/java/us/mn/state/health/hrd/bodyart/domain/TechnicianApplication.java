package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;
import java.util.List;

@Data
public class TechnicianApplication {

    private String applicationId;

    private ApplicationType applicationType;
    private ApplyMethodType applyMethod;

    private String category;
    private String subCategory;

    private String lastName;
    private String firstName;
    private String middleName;

    private String personalPhone;
    private String workPhone;

    private String emailAddress;

    private String supervisedEstName;
    private String supervisedEstFullAddress;
    private String supervisedEstPhoneNumber;
    private String supervisedEstWebsite;
    private String supervisedEstHours;

    private String additionalInformation;

    private List<AttachmentRepresentation> attachments;
    private List<EmployerRepresentation> employers;
    private List<NameRepresentation> otherNames;
    private List<SupervisorRepresentation> mnSupervisor;
    private List<QuestionResponseRepresentation> questionResponses;
    private List<AddressRepresentation> address;
    private LicenseRepresentation license;

}
