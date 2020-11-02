package us.mn.state.health.hrd.bodyart.domain;

import lombok.Data;

@Data
public class MakePaymentResponse {

    private String billerId;
    private String billerGroupId;
    private String session;

}
