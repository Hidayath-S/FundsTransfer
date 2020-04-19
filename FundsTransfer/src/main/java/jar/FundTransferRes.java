package jar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Transaction")
public class FundTransferRes {
	
	@Id
	private String paymentId;
	private String paymentStatus;
	private String custId;
	private long fromAcctNo;
	private long toAcctNo;
	private double amount;
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
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
	
	
	
	

}
