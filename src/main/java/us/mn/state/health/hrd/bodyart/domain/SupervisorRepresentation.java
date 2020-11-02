package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class SupervisorRepresentation {
    private String id;
    private String applicationId;

    private String name;
    private String licenseNbr;
}
