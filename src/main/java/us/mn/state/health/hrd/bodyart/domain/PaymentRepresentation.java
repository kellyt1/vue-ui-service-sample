package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRepresentation {

    private String paymentId;
    private String applicationId;

    private PaymentType paymentType;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private String status;
    private Integer number;
    private String name;
    private BigDecimal amount;
}
