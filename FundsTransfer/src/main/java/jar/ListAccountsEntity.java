package jar;




public class ListAccountsEntity {
	
	
	private String accountNumber;
	
	private double balance;
	
	private String custId;
	private String acctSts;
	
	public ListAccountsEntity() {
		super();
		
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
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
	
	
	

}
