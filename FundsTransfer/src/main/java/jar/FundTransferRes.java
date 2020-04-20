package jar;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Transaction")
public class FundTransferRes {
	
	@Id
	private int paymentId;
	private String paymentStatus;
	private String custId;
	private long fromAcctNo;
	private long toAcctNo;
	private double amount;
	private String remarks;
	private LocalDate paymentCreatedDate;
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public long getFromAcctNo() {
		return fromAcctNo;
	}
	public void setFromAcctNo(long fromAcctNo) {
		this.fromAcctNo = fromAcctNo;
	}
	public long getToAcctNo() {
		return toAcctNo;
	}
	public void setToAcctNo(long toAcctNo) {
		this.toAcctNo = toAcctNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public LocalDate getPaymentCreatedDate() {
		return paymentCreatedDate;
	}
	public void setPaymentCreatedDate(LocalDate paymentCreatedDate) {
		this.paymentCreatedDate = paymentCreatedDate;
	}
	
	
	
	
	

}
