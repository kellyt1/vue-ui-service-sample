package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import us.mn.state.health.hrd.bodyart.domain.LicenseType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Data
@Table(name="license")
public class License implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "license_type")
	private LicenseType licenseType;

	@Column(name = "status")
	private String status;

	@Column(name = "effective_date")
	private LocalDate effectiveDate;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "social_security_number")
	private String socialSecurityNumber;

	@Column(name = "number")
	private String number;

	@Column(name = "renewal_token")
	private String renewalToken;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "license_category")
	private String licenseCategory;

	@Column(name = "license_sub_category")
	private String licenseSubCategory;

	@Column(name = "pending_application")
	private Boolean pendingApplication;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="license")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Application> applications = new ArrayList<>();

}