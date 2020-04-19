package jar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class ListAccountsEntity {
	
	
	private long accountNumber;
	
	private double balance;
	
	private String custId;
	private String acctSts;
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getAcctSts() {
		return acctSts;
	}
	public void setAcctSts(String acctSts) {
		this.acctSts = acctSts;
	}
	public ListAccountsEntity() {
		super();
		
	}
	
	
	

}
