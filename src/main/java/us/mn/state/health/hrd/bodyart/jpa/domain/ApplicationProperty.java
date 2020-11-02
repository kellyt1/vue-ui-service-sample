package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="application_property")
public class ApplicationProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String key;

	private String value;

	public ApplicationProperty() { }

	public ApplicationProperty(String key) {
		this.key = key;
	}
}