package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import us.mn.state.health.hrd.bodyart.domain.ApplicationType;
import us.mn.state.health.hrd.bodyart.domain.ApplyMethodType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name="application")
public class Application implements Serializable {

	public Application() {
		this.otherLicenses = new ArrayList<>();
		this.payments = new ArrayList<>();
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "application_type")
	private ApplicationType applicationType;

	@Enumerated(EnumType.STRING)
	@Column(name = "apply_method")
	private ApplyMethodType applyMethod;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "personal_phone")
	private String personalPhone;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "supervised_est_name")
	private String supervisedEstName;

	@Column(name = "supervised_est_full_address")
	private String supervisedEstFullAddress;

	@Column(name = "supervised_est_phone_number")
	private String supervisedEstPhoneNumber;

	@Column(name = "supervised_est_website")
	private String supervisedEstWebsite;

	@Column(name = "supervised_est_hours")
	private String supervisedEstHours;

	@Column(name = "additional_information")
	private String additionalInformation;

	@Column(name = "adverse_action_taken")
	private Boolean adverseActionTaken;

	@Column(name = "adverse_action_text")
	private String adverseActionText;

	@Column(name = "conviction")
	private Boolean conviction;

	@Column(name = "conviction_text")
	private String convictionText;

	@Column(name = "received_date")
	private LocalDate receivedDate;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "license_id", referencedColumnName = "id")
	private License license;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<QuestionResponse> questionResponses = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Payment> payments = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Employer> employers = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OtherNames> otherNames = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MnSupervisor> mnSupervisors = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Attachment> attachments = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OtherLicense> otherLicenses = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Address> addresses = new ArrayList<>();

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Address getPreferredAddress() {
		return getAddresses().stream().filter(Address::getPreferred).collect(Collectors.toList()).get(0);
	}
}
