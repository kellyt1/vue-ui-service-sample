package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OtherLicenseRepresentation {

    private String otherLicenseId;
    private String applicationId;

    private String state;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateIssued;
    private String licenseId;

}
