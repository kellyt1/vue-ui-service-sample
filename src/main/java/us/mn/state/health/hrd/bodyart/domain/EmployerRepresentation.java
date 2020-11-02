package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployerRepresentation {

    private String employerId;
    private String applicatonId;

    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String phone;
    private String emailAddress;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
}
