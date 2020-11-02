package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name="question")
public class Question implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String code;

	@Column(name = "question_text")
	private String questionText;

	@Column(name = "trigger_addl_response")
	private Boolean triggerAddlResponse;

	@Column(name = "addl_response_text")
	private String addlResponseText;

}