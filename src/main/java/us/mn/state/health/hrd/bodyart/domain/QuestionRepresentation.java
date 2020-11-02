package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class QuestionRepresentation {

    private String code;
    private String questionText;
    private Boolean triggerAddlResponse;
    private String addlResponseText;
}
