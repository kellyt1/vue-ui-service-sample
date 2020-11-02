package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LicenseRepresentation {
    private String licenseId;
    private String applicatonId;

    private LicenseType licenseType;
    private String socialSecurityNumber;
    private String status;
    private String licenseNbr;
    private String renewalToken;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expirationDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
}

