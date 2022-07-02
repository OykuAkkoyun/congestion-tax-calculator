package com.volvo.congestiontaxcalculator;

import com.volvo.congestiontaxcalculator.service.model.CongestionTaxCalculatorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CongestionTaxCalculatorApplicationTests
{
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;


	@Test
	public void whenConfigNotFound() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Istanbul\", \"vehicleType\": \"Car\",\"dates\": [\"2013-02-01 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals("City Config is not found for: Istanbul", response.getBody().getDetailMessage());
		assertEquals(-1, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenCityNameIsNull() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"vehicleType\": \"Emergency\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals("Input cannot be null: cityName", response.getBody().getDetailMessage());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenExemptVehicleOnInputForStockholm() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Stockholm\", \"vehicleType\": \"Emergency\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenExemptVehicleOnInput() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Emergency\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenDateIsHoliday() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Car\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenDateBeforeHoliday() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Car\",\"dates\": [\"2013-03-28 06:27:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenDateIsSunday() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Car\",\"dates\": [\"2013-01-06 08:00:00\"]}", headers);
		ResponseEntity<CongestionTaxCalculatorResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/tax/calculator").toString(), request, CongestionTaxCalculatorResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
