package us.mn.state.health.hrd.bodyart.jpa.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import us.mn.state.health.hrd.bodyart.domain.PaymentType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name="payment")
public class Payment implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private PaymentType paymentType;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "status")
	private String status;

	@Column(name = "tx_number")
	private Integer txNumber;

	@Column(name = "fee_type")
	private String feeType;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "confirmation_number")
	private String confirmationNumber;

	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName = "id")
	private Application application;

	@ManyToOne
	@JoinColumn(name = "deposit_id", referencedColumnName = "id")
	private Deposit deposit;

}