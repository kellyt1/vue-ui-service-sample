package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class QuestionResponseRepresentation {
    private String id;
    private String applicationId;

    private String response;
    private String additionalResponse;
    private String question;
}
