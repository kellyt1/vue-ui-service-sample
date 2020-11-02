package us.mn.state.health.hrd.bodyart.jpa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.GenericGenerator;
import us.mn.state.health.hrd.bodyart.domain.AddressType;
import us.mn.state.health.hrd.bodyart.domain.LicenseType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Data
@Table(name="license_current")
public class LicenseCurrent implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "license_type")
	private LicenseType licenseType;

	@Column(name = "status")
	private String status;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "effective_date")
	private LocalDate effectiveDate;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "number")
	private String number;

	@Column(name = "renewal_token")
	private String renewalToken;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "street_address")
	private String streetAddress;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "personal_phone")
	private String personalPhone;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "license_category")
	private String licenseCategory;

	@Column(name = "license_sub_category")
	private String licenseSubCategory;

	@Column(name = "pending_application")
	private Boolean pendingApplication;

	@Enumerated(EnumType.STRING)
	@Column(name = "address_type")
	private AddressType addressType;

	@Column(name = "preferred")
	private Boolean preferred;

	@Column(name = "mn_supervisor")
	private String mnSupervisor;

	@Column(name = "other_names")
	private String otherNames;

	@Column(name = "discipline")
	private String discipline;

	@Column(name = "discipline_link")
	private String disciplineLink;

	@Column(name = "discipline_name")
	private String disciplineName;

	@Transient
	public String getEffectivePeriod() {
		StringBuilder sb = new StringBuilder();
		if (this.effectiveDate == null) {
			sb.append("n/a");
		} else {
			sb.append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(this.effectiveDate));
			sb.append(" - ");
			sb.append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(this.expirationDate));
		}
		return sb.toString();
	}

	@Transient
	public String getFormattedStatus() {
		return WordUtils.capitalizeFully(this.status);
	}

	@Transient
	public String getFormattedLicenseType() {
		return WordUtils.capitalizeFully(this.licenseType.getDescription());
	}

	@Transient
	public String getFormattedCategory() {
		StringBuilder sb = new StringBuilder();
		sb.append(WordUtils.capitalizeFully(this.licenseCategory));
		if (StringUtils.isNotEmpty(this.licenseCategory)) {
			sb.append(": ");
			sb.append(WordUtils.capitalizeFully(this.licenseSubCategory));
		}
		return sb.toString();
	}
}