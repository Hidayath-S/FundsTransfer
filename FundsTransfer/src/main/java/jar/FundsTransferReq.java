package jar;

import javax.validation.constraints.NotNull;

public class FundsTransferReq {
	
	
	@NotNull(message="CustId can't be null")
	private String custId;
	@NotNull(message="fromAcctNo can't be null")
	private long fromAcctNo;
	@NotNull(message="toAcctNo can't be null")
	private long toAcctNo;
	@NotNull(message="amount can't be null")
	private double amount;
	private String remarks;
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
	
	
	

}
