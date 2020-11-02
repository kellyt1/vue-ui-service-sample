package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class TokenPayload {

    private String programCode;

    private String subject;

    private String invoiceId;
    private String credentialId;
    private String payorId;
    private String renewalYear;

    private int daysToLive;

    private String bypassPayment;
    private String shortagePayment;

}

