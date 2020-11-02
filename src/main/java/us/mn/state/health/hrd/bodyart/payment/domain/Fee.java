package us.mn.state.health.hrd.bodyart.payment.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Fee {

	private String description;
	private String code;
	private Integer quantity;
	private BigDecimal amount;
	private String speedChart;

}