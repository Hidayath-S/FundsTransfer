package jar;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FundsTransferReq {
	
	
	@NotBlank(message="CustId can't be blank")
	private String custId;
	
	private long fromAcctNo;
	
	private long toAcctNo;
	@DecimalMin(value="0.1",inclusive=true,message="min amount should be 0.1")
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
