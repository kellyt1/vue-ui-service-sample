package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AttachmentType {


    DL("DL", "Driver's License", true),


    BIRTHCERT("BC", "Birth Certificate", true),


    MILITARYID("MI", "Military ID", true),


    PASSPORT("PP", "Passport", true),


    TRIBALID("TI", "Tribal ID", true),


    OTHER("OT", "Other Proof of Age", true),


    COMPCERT("CC","Certificate of Completion", false),


    DISCIPLINE("DI", "Disciplinary Action", false),


    HRSLOG("HL", "Hours Logged", false),


    SUPLOG("SL", "Log of Supervised Peircings", false),


    AFFIDAVIT("NA", "Notarized Affidavit", false),


    LICVERIFY("VC", "Licensing Verification of Credential", false),


    CONED("CE", "Continuing Education", false);

    private String code;
    private final String description;
    private final Boolean proofOfAge;

    private AttachmentType(String code, String description, Boolean proofOfAge) {
        this.code = code;
        this.description = description;
        this.proofOfAge = proofOfAge;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getProofOfAge() {
        return proofOfAge;
    }

    public static AttachmentType getByCode(String value) {
        List<AttachmentType> list = Arrays.asList(AttachmentType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

    public static List<AttachmentType> poaValues() {
        return Arrays.stream(values()).filter(at -> at.proofOfAge).collect(Collectors.toList());
    }

    public static List<AttachmentType> nonPoaValues() {
        return Arrays.stream(values()).filter(at -> !at.proofOfAge).collect(Collectors.toList());
    }

    @JsonCreator
    public static AttachmentType forValues(@JsonProperty("code") String code) {
        return getByCode(code);
    }
}