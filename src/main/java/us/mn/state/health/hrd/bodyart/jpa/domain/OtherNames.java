package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name="other_names")
public class OtherNames implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName = "id")
	private Application application;

}