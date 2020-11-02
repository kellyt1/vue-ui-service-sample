package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import us.mn.state.health.hrd.bodyart.domain.AttachmentType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name="attachment")
public class Attachment implements Serializable {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "attachment_type")
	private AttachmentType attachmentType;

	@Column(name = "other_attachment_type")
	private String otherAttachmentType;

	@Column(name = "file_name")
	private String filename;

	@Column(name = "url")
	private String url;

	@Column(name = "course_date")
	private LocalDate courseDate;

	@Column(name = "training_presenter")
	private String trainingPresenter;

	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName = "id")
	private Application application;
}