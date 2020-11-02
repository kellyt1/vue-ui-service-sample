package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class NameRepresentation {
    private String id;
    private String applicationId;

    private String lastName;
    private String firstName;
    private String middleName;
}
