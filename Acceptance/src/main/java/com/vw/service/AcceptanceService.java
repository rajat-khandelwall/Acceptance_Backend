package com.vw.service;

import java.io.IOException;
import java.util.List;

import com.vw.model.SignupEmployee;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import com.vw.model.ProjectDetails;

public interface AcceptanceService {

	public ResponseEntity<String> createProject(ProjectDetails projectDetails) throws InvalidFormatException, IOException;
	public ResponseEntity<String> createProjectLocation(ProjectDetails projectDetails) throws InvalidFormatException, IOException;
	public ProjectDetails getProject(String agrmntNumber, String generatedDate);
	public ProjectDetails getProjectLocation(String agrmntNumber, String generatedDate, String location);
	List<String> getAgreementNumbers(String brand);
	List<String> getAgreementNumbersLocation(String brand, String location);
	ResponseEntity<String> updateBilling(ProjectDetails projectDetails);
	ResponseEntity<String> updateBillingLocation(ProjectDetails projectDetails);
	ResponseEntity<String> signupEmployee(SignupEmployee employee);
	boolean validateLogin(String emailId, String password);
}