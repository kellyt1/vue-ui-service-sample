package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttachmentRepresentation {

    private String attachmentId;
    private String applicatonId;

    private AttachmentType type;

    private String filename;
    private boolean delete;
    private String url;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate courseDate;
    private String trainingPresenter;

}
