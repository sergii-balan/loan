package com.balan.sergii.loan.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.balan.sergii.loan.controller.LoansController;
import com.balan.sergii.loan.dao.Loan;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TestLoansController {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@DisplayName("PUT loan: max interstsRate 1.5 for time between 00:00 and 08:00")
    public void testCreateLoanMaxInterestRate() {
		String json = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T01:08\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		Loan result = doExchange("/loans", HttpMethod.PUT, json);
		
		assertNotNull(result.getId());
		assertEquals((Double)1.5, result.getInterestRate());
    }

	@Test
	@DisplayName("PUT loan: interestsRate no risk")
    public void testCreateLoanInterestRateNoRisk() {
		String json = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:08\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		Loan resultLoan = doExchange("/loans", HttpMethod.PUT, json);
		
		assertNotNull(resultLoan.getId());
		assertEquals((Double)1.1, resultLoan.getInterestRate());
    }
	
	@Test
	@DisplayName("POST loan: loan extenstion leads to max interest rate 1.5")
    public void update() {
		
		String json = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		Loan loan = doExchange("/loans", HttpMethod.PUT, json);
		
		assertNotNull(loan.getId());
		assertEquals((Double)1.1, loan.getInterestRate());
		
		DateTime extendedDate = new DateTime(loan.getCloseDate()).plusDays(1);
		loan.setCloseDate(extendedDate.toDate());
		
		Loan extendedLoan = doExchange("/loans", HttpMethod.POST, loan);
		
		assertEquals(loan.getId(), extendedLoan.getMasterId());
		assertEquals((Double)1.5, extendedLoan.getInterestRate());
    }     

	
	@Test
	@DisplayName("GET all Loans by UserId")
	public void getListByUserId() {
		String json2 = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		doExchange("/loans", HttpMethod.PUT, json2);
		doExchange("/loans", HttpMethod.PUT, json2);
		
		String json3 = "{"
				+ "\"userId\": 3,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		doExchange("/loans", HttpMethod.PUT, json3);		
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LoansController.HDR_USER, "2");
				
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
				
		HttpEntity<Loan[]> response = restTemplate.exchange("/loans", HttpMethod.GET, requestEntity, Loan[].class);
		Loan[] resultLoan = response.getBody();
		
		assertEquals(2, resultLoan.length);
	}
  
	@Test
	@DisplayName("GET loan by Id: /loans/{loanId}")
	public void getLoanById() {
		String json = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		Loan loan = doExchange("/loans", HttpMethod.PUT, json);
		
		assertNotNull(loan.getId());
		assertEquals((Double)1.1, loan.getInterestRate());
		
		Loan loanById = doExchange("/loans/" + loan.getId(), HttpMethod.GET, loan);
		
		assertEquals(loan.getId(), loanById.getId());
	}
	
	@Test
	@DisplayName("PUT loan: user can't requst more then 3 loans from the same IP")
	public void ipRestriction() {
		String json2 = "{"
				+ "\"userId\": 2,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		String json4 = "{"
				+ "\"userId\": 4,"
				+ "\"amount\": 10,"
				+ "\"interestRate\": 1.1,"
				+ "\"startDate\": \"2019-05-26T09:00\","
				+ "\"closeDate\": \"2019-05-27\""
				+ "}";
		
		doExchange("/loans", HttpMethod.PUT, json2);
		doExchange("/loans", HttpMethod.PUT, json2);
		doExchange("/loans", HttpMethod.PUT, json4);
		
		assertThrows(RuntimeException.class, () -> {
			doExchange("/loans", HttpMethod.PUT, json4);
	    });
		

		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LoansController.HDR_USER, "4");
				
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
				
		HttpEntity<Loan[]> response = restTemplate.exchange("/loans", HttpMethod.GET, requestEntity, Loan[].class);
		Loan[] resultLoan = response.getBody();
		
		assertEquals(1, resultLoan.length);
		
		assertEquals((Long)4L, resultLoan[0].getUserId());
	}
	
	private Loan doExchange(String url, HttpMethod method, String json) {		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
				
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
				
		HttpEntity<Loan> response = restTemplate.exchange(url, method, requestEntity, Loan.class);
		Loan resultLoan = response.getBody();
		return resultLoan;
    }
	
	private Loan doExchange(String url, HttpMethod method, Loan loan) {
		//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
				
		HttpEntity<Loan> requestEntity = new HttpEntity<Loan>(loan, headers);
				
		HttpEntity<Loan> response = restTemplate.exchange(url, method, requestEntity, Loan.class);
		Loan resultLoan = response.getBody();
		return resultLoan;
    }

}
