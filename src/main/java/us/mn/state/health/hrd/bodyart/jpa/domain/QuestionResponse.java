package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="question_response")
public class QuestionResponse implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "response")
	private Boolean response;

	@Column(name = "additional_response")
	private String additionalResponse;

	@ManyToOne
	@JoinColumn(name = "question_code", referencedColumnName = "code")
	private Question question;

	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName = "id")
	private Application application;

}