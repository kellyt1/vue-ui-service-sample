package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import us.mn.state.health.hrd.bodyart.domain.AddressType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    @Column(name = "preferred")
    private Boolean preferred;

    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;
}
