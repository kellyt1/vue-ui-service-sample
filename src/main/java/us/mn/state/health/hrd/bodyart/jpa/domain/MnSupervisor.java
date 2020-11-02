package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="mn_supervisor")
public class MnSupervisor implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "license_nbr")
	private String licenseNbr;

	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName = "id")
	private Application application;
}