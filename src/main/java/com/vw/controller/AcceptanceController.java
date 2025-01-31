package com.vw.controller;

import static com.vw.utility.Constants.ACCEPTANCE;
import static com.vw.utility.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.vw.model.SignupEmployee;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vw.model.ProjectDetails;
import com.vw.service.AcceptanceService;	

@RestController 
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(ACCEPTANCE)
public class AcceptanceController {
	Logger log = LoggerFactory.getLogger(AcceptanceController.class);
	@Autowired
	private AcceptanceService acceptanceService;

	@PostMapping(value = CREATE_PROJECT)
	public ResponseEntity<String> createProject(@RequestBody ProjectDetails projectDetails) throws InvalidFormatException, IOException {
		log.info("In Controller......");
		ResponseEntity<String> projectResponse = acceptanceService.createProject(projectDetails);
		log.info("Data has been stored and Report has been generated...");
		return projectResponse;
	}
	
	@PostMapping(value = CREATE_LOC_PROJECT)
	public ResponseEntity<String> createProjectLocation(@RequestBody ProjectDetails projectDetails) throws InvalidFormatException, IOException {
		log.info("In Controller......");
		ResponseEntity<String> projectResponse = acceptanceService.createProjectLocation(projectDetails);
		log.info("Data has been stored and Report has been generated...");
		return projectResponse;
	}

	@GetMapping(value = AGRMNT_NUMBER_GEN_DATE)
	public ResponseEntity<ProjectDetails> getProjectDetail(@PathVariable String agrmntNumber, @PathVariable String generatedDate) {
		ProjectDetails proDetails = acceptanceService.getProject(agrmntNumber, generatedDate);
		return ResponseEntity.ok().body(proDetails);
	}
	
	@GetMapping(value = AGRMNT_NUMBER_GEN_DATE_LOCATION)
	public ResponseEntity<ProjectDetails> getProjectDetailLocation(@PathVariable String agrmntNumber, @PathVariable String generatedDate, @PathVariable String location) {
		ProjectDetails proDetails = acceptanceService.getProjectLocation(agrmntNumber, generatedDate, location);
		return ResponseEntity.ok().body(proDetails);
	}

	@GetMapping(value = AGRMNT)
	public ResponseEntity<List<String>> getAgreementNumbers(@RequestParam(name = "brand") String brand) {
		List<String> agreementNumbers = acceptanceService.getAgreementNumbers(brand);
		return ResponseEntity.ok().body(agreementNumbers);
	}

	@PostMapping(value = UPDATE_BILLING)
	public ResponseEntity<String> updateBilling(@RequestBody ProjectDetails projectDetails){
		ResponseEntity<String> updateResponse = acceptanceService.updateBilling(projectDetails);
		log.info("Billing updated successfully");
		return updateResponse;
	}
	
	@PostMapping(value = UPDATE_BILLING_LOCATION)
	public ResponseEntity<String> updateBillingLocation(@RequestBody ProjectDetails projectDetails){
		ResponseEntity<String> updateResponse = acceptanceService.updateBillingLocation(projectDetails);
		log.info("Billing updated successfully");
		return updateResponse;
	}
	
	@PostMapping(value = DOWNLOAD_REPORT)
    public ResponseEntity<FileSystemResource> downloadReports(@RequestBody String projectName) {
        String sourceDir = "/home/ec2-user";  // Directory where reports are stored
        String zipFileName = "/home/ec2-user/Acceptance/" + projectName + ".zip";  // Location for the ZIP file
		
//		String sourceDir = "C:\\Users\\EA17TAE.VW\\Downloads\\ap-backend-develop\\ap-backend-develop\\Acceptance";
//		String zipFileName = "C:\\Users\\EA17TAE.VW\\Downloads\\ap-backend-develop\\ap-backend-develop\\Acceptance\\" + projectName + ".zip";

        // Ensure the directory exists
        Path zipFilePath = Paths.get(zipFileName);
        Path zipFileParentDir = zipFilePath.getParent();
        if (!Files.exists(zipFileParentDir)) {
            try {
                Files.createDirectories(zipFileParentDir);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        try (
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            for (String month : List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
                    "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")) {
                String reportFileName = "Acceptance-" + projectName + "-" + month + "-2024.docx";
                Path reportPath = Paths.get(sourceDir, reportFileName);
                if (Files.exists(reportPath)) {
                    ZipEntry zipEntry = new ZipEntry(reportFileName);
                    zos.putNextEntry(zipEntry);
                    Files.copy(reportPath, zos);
                    zos.closeEntry();
                } else {
                    // Log the missing file rather than printing to console
                    System.err.println("File not found: " + reportFileName);
                }
            }
            // No need to explicitly close streams as try-with-resources handles it
        } catch (IOException e) {
            // Log the error
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File file = new File(zipFileName);
        if (!file.exists()) {
            // Handle the case where the file is not created
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(ATTACHMENT, projectName + ".zip");
        headers.setContentLength(file.length());

        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }
	
	@PostMapping(value = DOWNLOAD_REPORT_LOCATION)
	public ResponseEntity<FileSystemResource> downloadReportLocation(@RequestBody ProjectDetails projectDetails) {
		
        String sourceDir = "/home/ec2-user";  // Directory where reports are stored
//		String sourceDir = "C:\\Users\\EA17TAE.VW\\Downloads\\ap-backend-develop\\ap-backend-develop\\Acceptance";

	    LocalDate date = LocalDate.parse(projectDetails.getGeneratedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
	    String month = date.getMonth().toString().toUpperCase();

	    String reportFileName = "Acceptance" + "-" + projectDetails.getProjectName() + "-" + month + "-" + projectDetails.getLocation() +"-2024.docx";
	    Path reportPath = Paths.get(sourceDir, reportFileName);

	    try {
	        File file = reportPath.toFile();
	        if (file.exists()) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData(ATTACHMENT, reportFileName);
	            headers.setContentLength(file.length());

	            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
	        } else {
	            System.out.println("File not found: " + reportFileName);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping(value = UPDATE_REPORT_LOCATION)
	public ResponseEntity<FileSystemResource> downloadReport(@RequestBody ProjectDetails projectDetails) {
        String sourceDir = "/home/ec2-user";  // Directory where reports are stored

        LocalDate date = LocalDate.parse(projectDetails.getGeneratedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
	    String month = date.getMonth().toString().toUpperCase();

	    String[] locations = {PUNE, GURUGRAM, BANGLORE};
	    List<Path> files = new ArrayList<>();
 
	    try {
	        for (String location : locations) {
	            String reportFileName = "Acceptance" + "-" + projectDetails.getProjectName() + "-" + month + "-" + location + "-2024.docx";
	            Path reportPath = Paths.get(sourceDir, reportFileName);
	            if (Files.exists(reportPath)) {
	                files.add(reportPath);
	            }
	        }

	        if (files.size() == 1) {
	            File file = files.get(0).toFile();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData(ATTACHMENT, file.getName());
	            headers.setContentLength(file.length());

	            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
	        } else if (files.size() > 1) {
	            Path zipFilePath = Paths.get(sourceDir, ZIP_FILE_NAME);

	            try (FileOutputStream fos = new FileOutputStream(zipFilePath.toFile());
	                 ZipOutputStream zos = new ZipOutputStream(fos)) {
	                for (Path file : files) {
	                    ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
	                    zos.putNextEntry(zipEntry);
	                    Files.copy(file, zos);
	                    zos.closeEntry();
	                }
	            }

	            File zipFile = zipFilePath.toFile();
	            if (!zipFile.exists()) {
	                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	            }

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData(ATTACHMENT, ZIP_FILE_NAME);
	            headers.setContentLength(zipFile.length());

	            return new ResponseEntity<>(new FileSystemResource(zipFile), headers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping(value = SIGNUP)
	public ResponseEntity<String> employeeSignup(@RequestBody SignupEmployee employee) {
		return acceptanceService.signupEmployee(employee);
	}

	@PostMapping(value = LOGIN)
	public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
	    String emailId = credentials.get("emailId");
	    String password = credentials.get("password");

	    boolean isValidUser = acceptanceService.validateLogin(emailId, password);
	    if (isValidUser) {
	        return ResponseEntity.ok(SUCCESS_MSG);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}
	
	@PostMapping(value = "/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
	    String emailId = request.get("emailId");
	    System.out.println(emailId);
	    try {
	        boolean otpSent = acceptanceService.sendPasswordResetOtp(emailId); // Delegate OTP logic to the service layer
	        if (otpSent) {
	            return ResponseEntity.ok("OTP sent successfully to your email.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found in the system.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while sending OTP.");
	    }
	}

}