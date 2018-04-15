package com.example.hystrix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.hystrix.service.StudentService;

@RestController
public class CollegeController {

	@Autowired
	StudentService service;
	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/student/getSchoolDetails/{schoolname}", method = RequestMethod.GET)
	public String getStudents(@PathVariable String schoolname) {

		List<ServiceInstance> instances = discoveryClient
				.getInstances("student-producer"); //student-zuul-service
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString();
		System.out.println("URI IS :"+serviceInstance.getUri()+"base url is : "+baseUrl);
		baseUrl = baseUrl + "/getStudentDetailsForSchool/{college}";
		return service.getStudentData(baseUrl, schoolname);
	}

}
