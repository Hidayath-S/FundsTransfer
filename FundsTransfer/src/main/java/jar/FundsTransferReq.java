package jar;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FundsTransferReq {
	
	
	@NotBlank(message="CustId can't be blank")
	@NotNull(message="CustId can't be null")
	private String custId;
	@NotNull(message="From account number can't be null")
	@NotBlank(message="From account number shouldn't be blank")
	private String fromAcctNo;
	@NotNull(message="To account number can't be null")
	@NotBlank(message="To account number shouldn't be blank")
	private String toAcctNo;
	
	@DecimalMin(value="0.1",inclusive=true,message="min transaction amount should be 0.1")
	private double amount;
	private String remarks;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getFromAcctNo() {
		return fromAcctNo;
	}
	public void setFromAcctNo(String fromAcctNo) {
		this.fromAcctNo = fromAcctNo;
	}
	public String getToAcctNo() {
		return toAcctNo;
	}
	public void setToAcctNo(String toAcctNo) {
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
