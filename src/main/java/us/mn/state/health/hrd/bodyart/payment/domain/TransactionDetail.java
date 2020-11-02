package us.mn.state.health.hrd.bodyart.payment.domain;

import lombok.Data;

@Data
public class TransactionDetail {

	private String transactionId;
	private String confirmationNbr;
	private String paymentMethod;
	
}
