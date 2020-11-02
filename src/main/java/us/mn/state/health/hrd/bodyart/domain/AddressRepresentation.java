package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class AddressRepresentation {

    private AddressType addressType;

    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private Boolean preferred;
    private String applicationId;
    private String id;
}
