package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
//TODO - remove
public class ContinuingEducation {

    private String educationId;
    private String applicationId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfCourse;
    private String trainingPresenter;
    private AttachmentRepresentation attachment;
}
