package jar;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.boot.json.*;

import antlr.collections.List;


@RestController
@RequestMapping("/api")

public class FundsTransferController {
	@Autowired
	FundsTransferRepo repo;
	@Autowired
	private Environment env;
	

	ListAccountsEntity accts = new ListAccountsEntity();
	RestTemplate template = new RestTemplate();
	FundTransferRes res = new FundTransferRes();
	ErrorResponse er = new ErrorResponse();
	CustDetailsEntity cust=new CustDetailsEntity();
	

	@PostMapping(path = "payment", consumes = "application/json", produces = "application/json")
	public Object transferMoney(@Valid @RequestBody FundsTransferReq fundsTransferReq, Errors errors) throws Exception {
		String ListAcctBaseUrl=env.getProperty("ListAccounts.baseUrl");
		String updtAcctBaseUrl=env.getProperty("updateAccounts.baseUrl");
		String custDetailsBaseUrl=env.getProperty("custDetails.baseUrl");
		String response = "";

		if (errors.hasErrors()) {
			response = errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",  "));

			er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
			er.setErrorMessage(response);
			return ResponseEntity.ok(er);
		} else {
			ListAccountsEntity fromAcctsDetails = template.getForObject(ListAcctBaseUrl+
					 fundsTransferReq.getFromAcctNo(), ListAccountsEntity.class);
			

			boolean custIdSts = (!fromAcctsDetails.getCustId().equals(fundsTransferReq.getCustId()));
			boolean fromAcctBalance = fromAcctsDetails.getBalance() <= fundsTransferReq.getAmount();
			boolean fromAcctsSts = (!fromAcctsDetails.getAcctSts().equalsIgnoreCase("Active"));

			if (custIdSts == true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("From Account number is not valid for " + fundsTransferReq.getCustId());

				return er;
			} else if (fromAcctBalance == true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("amount is not enough");

				return er;
			} else if (fromAcctsSts == true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("From Account status is not Active");

				return er;
			}
			ListAccountsEntity toAcctsDetails = template.getForObject(ListAcctBaseUrl+
					 fundsTransferReq.getToAcctNo(), ListAccountsEntity.class);
			boolean toAcctSts = (!toAcctsDetails.getAcctSts().equalsIgnoreCase("Active"));
			if (toAcctSts == true) {
				er.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
				er.setErrorMessage("To Account status is not Active");

				return er;
			}

			if (custIdSts == false && fromAcctBalance == false && fromAcctsSts == false && toAcctSts == false) {

				res.setCustId(fundsTransferReq.getCustId());
				res.setFromAcctNo(fundsTransferReq.getFromAcctNo());
				res.setToAcctNo(fundsTransferReq.getToAcctNo());
				res.setAmount(fundsTransferReq.getAmount());
				res.setRemarks(fundsTransferReq.getRemarks());
				LocalDate localDate=LocalDate.now();
				res.setPaymentCreatedDate(localDate);
				int paymentCount = (int) repo.count();
				paymentCount = paymentCount + 1;
				res.setPaymentId(paymentCount);
				res.setPaymentStatus("SUCCESS");
				repo.save(res);

				accts.setAccountNumber(res.getFromAcctNo());
				accts.setAcctSts("Active");
				accts.setBalance(fromAcctsDetails.getBalance() - res.getAmount());
				accts.setCustId(res.getCustId());
				String updateFromAcct = template.postForObject(updtAcctBaseUrl, accts, String.class);
				

				accts.setAccountNumber(res.getToAcctNo());
				accts.setAcctSts("Active");
				accts.setBalance(toAcctsDetails.getBalance() + res.getAmount());
				accts.setCustId(toAcctsDetails.getCustId());
				String updateToAcct = template.postForObject(updtAcctBaseUrl, accts, String.class);
				
				cust=template.getForObject(custDetailsBaseUrl+fundsTransferReq.getCustId(), CustDetailsEntity.class);
				
				Map<String, Object> custDetails=new HashMap<String, Object>();
				custDetails.put("custId", cust.getCustId());
				custDetails.put("FirstName", cust.getFirstName());
				custDetails.put("LastName", cust.getLastName());
				custDetails.put("SSN", cust.getSSN());
				custDetails.put("ECN", cust.getECN());
				custDetails.put("phoneNumber", cust.getPhoneNumber());
				
				
//				jsonObj.put("paymentId", fundsTransferReq.getCustId());
//				jsonObj.put("paymentStatus", "SUCCESS");
//				jsonObj.put("fromAcctNo", fundsTransferReq.getFromAcctNo());
//				jsonObj.put("toAcctNo", fundsTransferReq.getToAcctNo());
//				jsonObj.put("CustomerDetails", jsonObj1);
//				jsonObj.put("amount", fundsTransferReq.getAmount());
//				jsonObj.put("remarks", fundsTransferReq.getRemarks());
//				jsonObj.put("paymentCreatedDate", localDate);
				
				
				Map<String, Object> pmtDetails=new HashMap<String, Object>();
				
				pmtDetails.put("paymentId", paymentCount);
				pmtDetails.put("paymentStatus", "SUCCESS");
				pmtDetails.put("fromAcctNo", fundsTransferReq.getFromAcctNo());
				pmtDetails.put("toAcctNo", fundsTransferReq.getToAcctNo());
				pmtDetails.put("customerDetails", custDetails);
				pmtDetails.put("amount", fundsTransferReq.getAmount());
				pmtDetails.put("remarks", fundsTransferReq.getRemarks());
				pmtDetails.put("paymentCreatedDate", localDate);
				
				Map<String, Object> pmtResponse=new HashMap<String, Object>();
				pmtResponse.put("PaymentResponse", pmtDetails);
			
				

				return pmtResponse;
			}

			return "Payment creation was failed";

		}

	}

	@GetMapping(path = "payment/{paymentId}", produces = "application/json")
	public ResponseEntity getPaymentById(@PathVariable int paymentId) {

		boolean paymentSts = repo.existsById(paymentId);
		if (paymentSts == false) {

			er.setErrorCode(HttpStatus.BAD_REQUEST.value());
			er.setErrorMessage("Payment not found with paymentId = " + paymentId);
			return ResponseEntity.ok(er);
		}

		Optional<FundTransferRes> response = repo.findById(paymentId);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping(path = "payment/{paymentId}", produces = "application/json")
	public ResponseEntity deletePaymentById(@PathVariable int paymentId) {

		boolean paymentSts = repo.existsById(paymentId);
		if (paymentSts == false) {

			er.setErrorCode(HttpStatus.BAD_REQUEST.value());
			er.setErrorMessage("Payment not found with paymentId = " + paymentId);
			return ResponseEntity.ok(er);
		}

		repo.deleteById(paymentId);
		return ResponseEntity.ok("Payment deleted successfuly!");

	}

}
