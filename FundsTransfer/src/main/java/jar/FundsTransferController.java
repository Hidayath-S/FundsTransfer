package jar;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import antlr.collections.List;

@RestController
public class FundsTransferController {
	@Autowired
	FundsTransferRepo repo;
	
	
	
	@PostMapping(path="transferMoney", consumes="application/json",produces="application/json")
	public Object transferMoney(@Valid   @RequestBody FundsTransferReq fundsTransferReq, Errors errors){
		ListAccountsEntity accts= new ListAccountsEntity();
		RestTemplate template= new RestTemplate();
		FundTransferRes res= new FundTransferRes();
		String response="";
		ErrorResponse er= new ErrorResponse();
		if(errors.hasErrors()) {
			response=errors.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.joining(","));
			
			er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
			er.setErrorMessage(response);
			return ResponseEntity.ok(er);
		}
		else {
			 
			ListAccountsEntity fromAcctsDetails= template.getForObject("http://localhost:8114/Account/"+fundsTransferReq.getFromAcctNo(),ListAccountsEntity.class);
			
			boolean custIdSts=(!fromAcctsDetails.getCustId().equals(fundsTransferReq.getCustId()));
			boolean fromAcctBalance=fromAcctsDetails.getBalance()<=fundsTransferReq.getAmount();
			boolean fromAcctsSts=(!fromAcctsDetails.getAcctSts().equalsIgnoreCase("Active"));
			
			if(custIdSts==true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("From Account number is not valid for "+fundsTransferReq.getCustId());
				
				return er;
			}
			else if(fromAcctBalance==true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("amount is not enough");
				
				return er;
			}
			else if(fromAcctsSts==true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("From Account status is not Active");
				
				return er;
			}
			ListAccountsEntity toAcctsDetails= template.getForObject("http://localhost:8114/Account/"+fundsTransferReq.getToAcctNo(),ListAccountsEntity.class);
			boolean toAcctSts=(!toAcctsDetails.getAcctSts().equalsIgnoreCase("Active"));
			if(toAcctSts==true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("To Account status is not Active");
				
				return er;
			}
			
			if(custIdSts==false&&fromAcctBalance==false&&fromAcctsSts==false&&toAcctSts==false) {
				
				
				res.setCustId(fundsTransferReq.getCustId());
				res.setFromAcctNo(fundsTransferReq.getFromAcctNo());
				res.setToAcctNo(fundsTransferReq.getToAcctNo());
				res.setAmount(fundsTransferReq.getAmount());
				int paymentCount=(int) repo.count();
				paymentCount=paymentCount+1;
				String paySuf="PAYMENT";
				res.setPaymentId(paySuf+paymentCount);
				res.setPaymentStatus("SUCCESS");
				repo.save(res);
				
		
				accts.setAccountNumber(res.getFromAcctNo());
				accts.setAcctSts("Active");
				accts.setBalance(fromAcctsDetails.getBalance()-res.getAmount());
				accts.setCustId(res.getCustId());
				String updateFromAcct=template.postForObject("http://localhost:8114/Accounts", accts, String.class);
				
				accts.setAccountNumber(res.getToAcctNo());
				accts.setAcctSts("Active");
				accts.setBalance(toAcctsDetails.getBalance()+res.getAmount());
				accts.setCustId(toAcctsDetails.getCustId());
				String updateToAcct=template.postForObject("http://localhost:8114/Accounts", accts, String.class);
				
				
				return res;
			}
			
			
			return null;
			 
			 
			
		}
	
		
	}}


