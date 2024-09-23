package com.vw.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vw.model.ProjectDetails;
import com.vw.model.RemainingData;
import com.vw.repository.AcceptanceRepository;
import com.vw.repository.RemainingRepository;
import com.vw.service.AcceptanceService;
import com.vw.utility.Utilities;

@Service
public class AcceptanceServiceImpl implements AcceptanceService {
	Logger log = LoggerFactory.getLogger(AcceptanceServiceImpl.class);

	@Autowired
	private AcceptanceRepository repository;

	@Autowired
	private RemainingRepository rem_repository;

	ResponseEntity response = null;

	@Override
	public ResponseEntity<String> createProject(ProjectDetails projectDetails) {
		log.info("inside the createProject() method ");

		LocalDate generatedDate = Utilities.dateConvert(projectDetails.getGeneratedDate());
		LocalDate from = Utilities.dateConvert(projectDetails.getFromDate());
		LocalDate fromDate = LocalDate.of(from.getYear(), from.getMonth(), 1);
		LocalDate decemberEndDate = LocalDate.of(generatedDate.getYear(), Month.DECEMBER, 31);

		for (LocalDate date = fromDate; !date.isAfter(decemberEndDate); date = date.plusMonths(1)) {
			LocalDate fromDate1 = LocalDate.of(date.getYear(), date.getMonth(), 1);

			LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());
			LocalDate toDate1 = lastDate;
			projectDetails.setFromDate(fromDate1.toString());
			projectDetails.setToDate(toDate1.toString());

			if (generatedDate.getMonth() == date.getMonth()) {
				ProjectDetails projectClone = new ProjectDetails();

				try {
					Utilities.prepairePersistanceData(projectDetails, projectClone);
					Utilities.generateReport(projectDetails, date);
				} catch (IOException | InvalidFormatException e) {
					e.printStackTrace();
				}
				repository.save(projectClone);
				log.debug("Record has been Stored ");
				response = new ResponseEntity(HttpStatus.CREATED);

			} else {
				ProjectDetails previousReport = repository.findByAgrmntNumberAndGeneratedDate(
						projectDetails.getAgrmntNumber(), date.minusMonths(1).getMonthValue());
				Utilities.calculateCostPerMonth(projectDetails, previousReport);
				ProjectDetails projectClone = new ProjectDetails();
				projectDetails.setGeneratedDate(date.toString());
				Utilities.prepairePersistanceData(projectDetails, projectClone);

				try {
					Utilities.generateReport(projectDetails, date);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				}
				repository.save(projectClone);
				log.debug("Record has been Stored ");
				response = new ResponseEntity(HttpStatus.CREATED);
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<String> createProjectLocation(ProjectDetails projectDetails) {
		log.info("inside the createProject() method ");
		LocalDate generatedDate = Utilities.dateConvert(projectDetails.getGeneratedDate());
		LocalDate from = Utilities.dateConvert(projectDetails.getFromDate());
		LocalDate fromDate = LocalDate.of(from.getYear(), from.getMonth(), 1);
		LocalDate decemberEndDate = LocalDate.of(generatedDate.getYear(), Month.DECEMBER, 31);

		for (LocalDate date = fromDate; !date.isAfter(decemberEndDate); date = date.plusMonths(1)) {
			LocalDate fromDate1 = LocalDate.of(date.getYear(), date.getMonth(), 1);

			LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());
			LocalDate toDate1 = lastDate;
			projectDetails.setFromDate(fromDate1.toString());
			projectDetails.setToDate(toDate1.toString());

			if (generatedDate.getMonth() == date.getMonth()) {
				ProjectDetails projectClone = new ProjectDetails();

				try {
					Utilities.prepairePersistanceData(projectDetails, projectClone);
					Utilities.generateReport(projectDetails, date);
				} catch (IOException | InvalidFormatException e) {
					e.printStackTrace();
				}
				if (projectClone.getRemainingData() == null) {
					RemainingData existingRemData = rem_repository.findRemData(projectClone.getAgrmntNumber());
					existingRemData.setSrvcRemainBdgt(projectClone.getSrvcRemainBdgt());
					existingRemData.setMiscRemainBdgt(projectClone.getMiscRemainBdgt());
					existingRemData.setTotalRemainBdgt(projectClone.getTotalRemainBdgt());
					rem_repository.save(existingRemData);
				}
				repository.save(projectClone);
				log.debug("Record has been Stored ");
				response = new ResponseEntity(HttpStatus.CREATED);

			}
		}
		return response;
	}

	@Override
	public ProjectDetails getProject(String agrmntNumber, String generatedDate) {
		LocalDate date = Utilities.dateConvert(generatedDate);
		int month = date.getMonthValue();
		return repository.findByAgrmntNumberAndGeneratedDate(agrmntNumber, month);
	}

	@Override
	public ProjectDetails getProjectLocation(String agrmntNumber, String generatedDate, String location) {
		LocalDate date = Utilities.dateConvert(generatedDate);
		int month = date.getMonthValue();

		ProjectDetails projectDetails = repository.findByAgrmntNumberAndGeneratedDateAndLocation(agrmntNumber, month,
				location);
		if (projectDetails == null) {
			projectDetails = repository.findByAgrmntNumberAndGeneratedDateAndLocation(agrmntNumber, month, "Pune");
			if (projectDetails == null) {
				projectDetails = repository.findByAgrmntNumberAndGeneratedDateAndLocation(agrmntNumber, month,
						"Bangalore");
				if (projectDetails == null) {
					projectDetails = repository.findByAgrmntNumberAndGeneratedDateAndLocation(agrmntNumber, month,
							"Gurugram");
					if (projectDetails == null) {
						projectDetails = repository.findByAgrmntNumberAndGeneratedDateAndLocation(agrmntNumber,
								month - 1, location);

						RemainingData rem_data = rem_repository.findRemData(agrmntNumber);

						projectDetails.setSrvcMonthlyCost(0);
						projectDetails.setMiscMonthlyBdgt(0);
						projectDetails.setTotalMonthlyBdgt(0);
						projectDetails.setMiscPricing(0);
						projectDetails.setJustification("");
						projectDetails.setSrvcRemainBdgt(rem_data.getSrvcRemainBdgt());
						projectDetails.setMiscRemainBdgt(rem_data.getMiscRemainBdgt());
						projectDetails.setTotalRemainBdgt(rem_data.getTotalRemainBdgt());

						LocalDate generatedLocalDate = LocalDate.parse(generatedDate, DateTimeFormatter.ISO_DATE);
						YearMonth yearMonth = YearMonth.from(generatedLocalDate);
						LocalDate fromDate = yearMonth.atDay(1);
						LocalDate toDate = yearMonth.atEndOfMonth();

						projectDetails.setFromDate(fromDate.toString());
						projectDetails.setToDate(toDate.toString());
						projectDetails.setGeneratedDate(generatedDate);
					
				} else {

					RemainingData rem_data = rem_repository.findRemData(agrmntNumber);

					projectDetails.setLocation(location);
					projectDetails.setSrvcMonthlyCost(0);
					projectDetails.setMiscMonthlyBdgt(0);
					projectDetails.setTotalMonthlyBdgt(0);
					projectDetails.setMiscPricing(0);
					projectDetails.setJustification("");
					projectDetails.setSrvcRemainBdgt(rem_data.getSrvcRemainBdgt());
					projectDetails.setMiscRemainBdgt(rem_data.getMiscRemainBdgt());
					projectDetails.setTotalRemainBdgt(rem_data.getTotalRemainBdgt());

					return projectDetails;

				}
			} else {
				RemainingData rem_data = rem_repository.findRemData(agrmntNumber);

				projectDetails.setLocation(location);
				projectDetails.setSrvcMonthlyCost(0);
				projectDetails.setMiscMonthlyBdgt(0);
				projectDetails.setTotalMonthlyBdgt(0);
				projectDetails.setMiscPricing(0);
				projectDetails.setJustification("");
				projectDetails.setSrvcRemainBdgt(rem_data.getSrvcRemainBdgt());
				projectDetails.setMiscRemainBdgt(rem_data.getMiscRemainBdgt());
				projectDetails.setTotalRemainBdgt(rem_data.getTotalRemainBdgt());

				return projectDetails;
			}
		} else {

			RemainingData rem_data = rem_repository.findRemData(agrmntNumber);

			projectDetails.setLocation(location);
			projectDetails.setSrvcMonthlyCost(0);
			projectDetails.setMiscMonthlyBdgt(0);
			projectDetails.setTotalMonthlyBdgt(0);
			projectDetails.setMiscPricing(0);
			projectDetails.setJustification("");
			projectDetails.setSrvcRemainBdgt(rem_data.getSrvcRemainBdgt());
			projectDetails.setMiscRemainBdgt(rem_data.getMiscRemainBdgt());
			projectDetails.setTotalRemainBdgt(rem_data.getTotalRemainBdgt());

			return projectDetails;
		}
	}

	return projectDetails;

	}

	@Override
	public List<String> getAgreementNumbers(String brand) {
		List<String> agreementNumbers = repository.findAgreementNumbersByBrand(brand);
		return agreementNumbers;
	}

	@Override
	public List<String> getAgreementNumbersLocation(String brand, String location) {
		List<String> agreementNumbers = repository.findAgreementNumbersByBrandLocation(brand, location);
		return agreementNumbers;
	}

	@Override
	public ResponseEntity<String> updateBilling(ProjectDetails projectDetails) {
		Month month = Utilities.dateConvert(projectDetails.getToDate()).getMonth();

		LocalDate generatedDate = Utilities.dateConvert(projectDetails.getGeneratedDate());
		LocalDate fromDate = Utilities.dateConvert(projectDetails.getFromDate()).plusMonths(1);
		LocalDate decemberEndDate = LocalDate.of(generatedDate.getYear(), Month.DECEMBER, 31);

		if (!StringUtils.equals("JANUARY", month.toString())) {
			ProjectDetails preMonthRecord = repository.findByAgrmntNumberAndGeneratedDate(
					projectDetails.getAgrmntNumber(),
					Utilities.dateConvert(projectDetails.getGeneratedDate()).minusMonths(1).getMonthValue());
			Utilities.calculateCostPerMonth(projectDetails, preMonthRecord);
//			projectDetails.setGeneratedDate(LocalDate.now().toString());
			repository.save(projectDetails);

			try {
				Utilities.generateReport(projectDetails, Utilities.dateConvert(projectDetails.getFromDate()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}

			for (LocalDate date = fromDate; !date.isAfter(decemberEndDate); date = date.plusMonths(1)) {

				ProjectDetails currentReport = repository
						.findByAgrmntNumberAndGeneratedDate(projectDetails.getAgrmntNumber(), date.getMonthValue());
				ProjectDetails previousReport = repository.findByAgrmntNumberAndGeneratedDate(
						projectDetails.getAgrmntNumber(), date.minusMonths(1).getMonthValue());

				currentReport
						.setSrvcRemainBdgt(previousReport.getSrvcRemainBdgt() - currentReport.getSrvcMonthlyCost());
				// if (currentReport.getSrvcRemainBdgt() < 0)
				// currentReport.setSrvcRemainBdgt(0);
				currentReport
						.setMiscRemainBdgt(previousReport.getMiscRemainBdgt() - currentReport.getMiscMonthlyBdgt());
				// if (currentReport.getMiscRemainBdgt() < 0)
				// currentReport.setMiscRemainBdgt(0);
				currentReport.setTotalRemainBdgt(currentReport.getSrvcRemainBdgt() + currentReport.getMiscRemainBdgt());
				// if (currentReport.getTotalRemainBdgt() < 0)
				// currentReport.setTotalRemainBdgt(0);
				repository.save(currentReport);

				try {
					Utilities.generateReport(currentReport, Utilities.dateConvert(currentReport.getFromDate()));

				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				}
			}
		} else {
			Utilities.calculateCostPerMonth(projectDetails, null);
//			projectDetails.setGeneratedDate(LocalDate.now().toString());
			repository.save(projectDetails);

			try {
				Utilities.generateReport(projectDetails, Utilities.dateConvert(projectDetails.getFromDate()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}

			for (LocalDate date = fromDate; !date.isAfter(decemberEndDate); date = date.plusMonths(1)) {

				ProjectDetails currentReport = repository
						.findByAgrmntNumberAndGeneratedDate(projectDetails.getAgrmntNumber(), date.getMonthValue());
				ProjectDetails previousReport = repository.findByAgrmntNumberAndGeneratedDate(
						projectDetails.getAgrmntNumber(), date.minusMonths(1).getMonthValue());

				currentReport
						.setSrvcRemainBdgt(previousReport.getSrvcRemainBdgt() - currentReport.getSrvcMonthlyCost());
				// if (currentReport.getSrvcRemainBdgt() < 0)
				// currentReport.setSrvcRemainBdgt(0);
				currentReport
						.setMiscRemainBdgt(previousReport.getMiscRemainBdgt() - currentReport.getMiscMonthlyBdgt());
				// if (currentReport.getMiscRemainBdgt() < 0)
				// currentReport.setMiscRemainBdgt(0);
				currentReport.setTotalRemainBdgt(currentReport.getSrvcRemainBdgt() + currentReport.getMiscRemainBdgt());
				// if (currentReport.getTotalRemainBdgt() < 0)
				// currentReport.setTotalRemainBdgt(0);
				repository.save(currentReport);

				try {
					Utilities.generateReport(currentReport, Utilities.dateConvert(currentReport.getFromDate()));

				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				}
			}

		}
		return response;
	}
	
	@Override
	public ResponseEntity<String> updateBillingLocation(ProjectDetails projectDetails) {
	    int monthValue = Utilities.dateConvert(projectDetails.getGeneratedDate()).getMonthValue();
	  	repository.save(projectDetails);

	    List<String> locations = Arrays.asList("Bangalore", "Gurugram");
	    
	    if (projectDetails.getLocation().equals("Pune")) {
	        for (String location : locations) {
	            updateLocationData(projectDetails, monthValue, location);
	        }
	    } else if (projectDetails.getLocation().equals("Bangalore")) {
	        updateLocationData(projectDetails, monthValue, "Gurugram");
	    }

	    updateRemainingData(projectDetails);
	    return ResponseEntity.ok("Update successful");
	}

	private void updateLocationData(ProjectDetails projectDetails, int monthValue, String location) {
	    ProjectDetails locationData = repository.findByAgrmntNumberAndGeneratedDateAndLocation(
	        projectDetails.getAgrmntNumber(), monthValue, location
	    );

	    if (locationData != null) {
	        locationData.setSrvcRemainBdgt(projectDetails.getSrvcRemainBdgt() - locationData.getSrvcMonthlyCost());
	        locationData.setMiscRemainBdgt(projectDetails.getMiscRemainBdgt() - locationData.getMiscMonthlyBdgt());
	        locationData.setTotalRemainBdgt(locationData.getSrvcRemainBdgt() + locationData.getMiscRemainBdgt());

	        repository.save(locationData);	        
	        generateReport(locationData);
	    }
	}

	private void updateRemainingData(ProjectDetails projectDetails) {
	    RemainingData existingRemData = rem_repository.findRemData(projectDetails.getAgrmntNumber());
	    existingRemData.setSrvcRemainBdgt(projectDetails.getSrvcRemainBdgt());
	    existingRemData.setMiscRemainBdgt(projectDetails.getMiscRemainBdgt());
	    existingRemData.setTotalRemainBdgt(existingRemData.getSrvcRemainBdgt() + existingRemData.getMiscRemainBdgt());
	    rem_repository.save(existingRemData);

	    generateReport(projectDetails);
	}

	private void generateReport(ProjectDetails projectDetails) {
	    try {
	        Utilities.generateReport(projectDetails, Utilities.dateConvert(projectDetails.getFromDate()));
	    } catch (IOException | InvalidFormatException e) {
	        e.printStackTrace(); 
	    }
	}

}