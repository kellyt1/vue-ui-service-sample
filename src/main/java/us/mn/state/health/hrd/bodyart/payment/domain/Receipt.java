package us.mn.state.health.hrd.bodyart.payment.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Receipt {

	private LocalDate date;
	private Long transactionNbr;

	private String businessName;

	private String firstName;
	private String lastName;

	private String addressTxt1;
	private String addressTxt2;
	private String city;
	private String stateCode;
	private String zipCode;

	private String emailAddress;
	private String phoneNumber;


//	@Column(name="PAYMENT_CONFIRM_SOURCE", length=1, columnDefinition="char")
	//private String paymentConfirmSource;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
//	private Date createDate;
//
//	@Column(name="CREATOR_SEQ_NO")
//	private BigDecimal creatorSeqNo;
//
//	@Column(name="CREDIT_CARD_CODE", length=4, columnDefinition="char")
//	private String creditCardCode;
//
//	@Column(name="CREDIT_CARD_EXPIRE", length=5, columnDefinition="char")
//	private String creditCardExpire;

//	@Column(name="DOC_NO")
//	private String docNo;
//
//	@Column(name="EH_PROGRAM_CODE", length=4, columnDefinition="char")
//	private String ehProgramCode;
//
//	@Column(name="FINANCIAL_CODE", length=4, columnDefinition="char")
//	private String financialCode;
//
//	@Column(name="FISCAL_YEAR_CODE", length=4, columnDefinition="char")
//	private String fiscalYearCode;
//
//	@Column(name="INSTITUTION_NAME")
//	private String institutionName;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="MODIFIED_DATE")
//	private Date modifiedDate;
//
//	@Column(name="MODIFIER_SEQ_NO")
//	private BigDecimal modifierSeqNo;
//
//	@Column(name="MULTI_TRANSACTION_IND", length=1, columnDefinition="char")
//	private String multiTransactionInd;
//
//	private String notes;
//
//	@Column(name="PARTY_SEQ_NO")
//	private BigDecimal partySeqNo;
//
//	@Column(name="PAYMENT_CODE", length=4, columnDefinition="char")
//	private String paymentCode;
//
//	@Column(name="PAYMENT_STATUS_CODE", length=4, columnDefinition="char")
//	private String paymentStatusCode;
//
//	@Column(name="RECEIPT_AMT")
//	private BigDecimal receiptAmt;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="RECEIPT_DATE")
//	private Date receiptDate;
//
//	@Column(name="STATE_CODE", length=2, columnDefinition="char")
//
//
//	@Column(name="TRANS_CONFIRM_ID")
//	private String transConfirmId;
//
//	@Column(name="TRANSACTION_NBR")
//
//	//bi-directional many-to-one association to Fee
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "receipt", cascade = CascadeType.ALL)ALL
	private List<Fee> fees;

	public Receipt() {
		this.fees = new ArrayList<>();
	}

	public Fee addFee(Fee fee) {
		getFees().add(fee);
		return fee;
	}

	public Fee removeFee(Fee fee) {
		getFees().remove(fee);
		return fee;
	}
}