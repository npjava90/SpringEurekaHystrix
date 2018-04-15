package com.example.hystrix.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class StudentService {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getStudentData_Fallback")
	public String getStudentData(String baseUrl, String college) {
		//http://localhost:8112/getStudentDetailsForSchool/{college}
		
		baseUrl="http://localhost:8112/getStudentDetailsForSchool/{college}";
		String response = restTemplate
				.exchange(baseUrl
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
			}, college).getBody();

		

		return "Circuit Breaker is not active for : "+ response +" at : "+ new Date();
	}
	
	@SuppressWarnings("unused")
	private String getStudentData_Fallback(String baseUrl,String schoolname) {
		System.out.println("Student Service is down!!! fallback route enabled...");
		return "CIRCUIT BREAKER ENABLED!!!No Response From Student Service at this moment. Service will be up soon - " + new Date();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
