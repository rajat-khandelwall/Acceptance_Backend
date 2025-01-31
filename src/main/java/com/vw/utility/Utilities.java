package com.vw.utility;

import static com.vw.utility.Constants.ACCEPTANCE_REPORT;
import static com.vw.utility.Constants.ACCEPT_FILE;
import static com.vw.utility.Constants.ACCEPT_IMG;
import static com.vw.utility.Constants.ACC_EMU_VALUE_ONE;
import static com.vw.utility.Constants.AGREEMENT_NUMBER;
import static com.vw.utility.Constants.BILLING_PERIOD;
import static com.vw.utility.Constants.CURRENT_MONTH_BDGT;
import static com.vw.utility.Constants.DATE;
import static com.vw.utility.Constants.EIGHTEEN;
import static com.vw.utility.Constants.ELEVEN;
import static com.vw.utility.Constants.EMU_VALUE;
import static com.vw.utility.Constants.FIFTEEN;
import static com.vw.utility.Constants.FIVE;
import static com.vw.utility.Constants.FOUR;
import static com.vw.utility.Constants.FOURTEEN;
import static com.vw.utility.Constants.FOURTY_PER;
import static com.vw.utility.Constants.INPUT_FORMAT;
import static com.vw.utility.Constants.ITS_FILE;
import static com.vw.utility.Constants.ITS_IMG;
import static com.vw.utility.Constants.L1;
import static com.vw.utility.Constants.L2;
import static com.vw.utility.Constants.L3;
import static com.vw.utility.Constants.MISCELLANEOUS;
import static com.vw.utility.Constants.ONE;
import static com.vw.utility.Constants.ORDER_NUMBER;
import static com.vw.utility.Constants.OUTPUT_FORMATE;
import static com.vw.utility.Constants.PATTERN;
import static com.vw.utility.Constants.PROJECT;
import static com.vw.utility.Constants.PROVIDER;
import static com.vw.utility.Constants.REMAINING_BDGT;
import static com.vw.utility.Constants.RESPONSIBLE_PERSON;
import static com.vw.utility.Constants.SERVICE;
import static com.vw.utility.Constants.SERVICE_COST;
import static com.vw.utility.Constants.SERVICE_RECEIVER;
import static com.vw.utility.Constants.SEVENTEEN;
import static com.vw.utility.Constants.SIXTEEN;
import static com.vw.utility.Constants.SKODA_FILE;
import static com.vw.utility.Constants.SR;
import static com.vw.utility.Constants.TASK_LIST;
import static com.vw.utility.Constants.THIRTEEN;
import static com.vw.utility.Constants.THIRTY_FIVE_PER;
import static com.vw.utility.Constants.THREE;
import static com.vw.utility.Constants.TOTAL;
import static com.vw.utility.Constants.TOTAL_BUDGET;
import static com.vw.utility.Constants.TWELVE;
import static com.vw.utility.Constants.TWENTY_PER;
import static com.vw.utility.Constants.TWO;
import static com.vw.utility.Constants.ZERO;
import static com.vw.utility.Constants.ZERO_PER;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.vw.model.CostDetails;
import com.vw.model.LevelInfo;
import com.vw.model.ProjectDetails;
import com.vw.model.RemainingData;

public class Utilities {
	static Logger log = LoggerFactory.getLogger(Utilities.class);

	public static FileOutputStream generateReport(ProjectDetails projectDetails, LocalDate localDate)
			throws IOException, InvalidFormatException {
		log.info("inside the generateReport() Method");
		XWPFDocument accptanceReport = new XWPFDocument();
		XWPFTable table = accptanceReport.createTable(EIGHTEEN, FIVE);
		table.setCellMargins(ZERO, ZERO, ZERO, ZERO);
		try {
			Utilities.addImages(table, ZERO, ZERO, ITS_IMG, ITS_FILE, EMU_VALUE, EMU_VALUE);
			table.getRow(ZERO).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(THREE));
			Utilities.addImages(table, ZERO, ONE, ACCEPT_IMG, ACCEPT_FILE, ACC_EMU_VALUE_ONE, EMU_VALUE);
			Utilities.addImages(table, ZERO, TWO, ImageEnum.getImageByBrandName(projectDetails.getBrand()), SKODA_FILE,
					EMU_VALUE, EMU_VALUE);

		} catch (Exception e) {
			e.getStackTrace();
		}
		XWPFTableRow row = table.getRow(ZERO);
		int cellIndex = row.getTableCells().size() - 1;
		for (int i = cellIndex; i >= cellIndex - 1; i--) {
			row.getCell(i).getCTTc().newCursor().removeXml();
		}
		table.setTableAlignment(TableRowAlign.CENTER);

		CTVMerge vmerge = CTVMerge.Factory.newInstance();
		vmerge.setVal(STMerge.RESTART);
		table.getRow(ONE).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmerge);
		table.getRow(ONE).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmerge);

		CTVMerge vmerge1 = CTVMerge.Factory.newInstance();
		vmerge1.setVal(STMerge.CONTINUE);
		table.getRow(TWO).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmerge1);
		table.getRow(TWO).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmerge1);

		CTVMerge vmergeTwo = CTVMerge.Factory.newInstance();
		vmergeTwo.setVal(STMerge.RESTART);
		table.getRow(THREE).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeTwo);
		table.getRow(THREE).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeTwo);

		CTVMerge vmergeThree = CTVMerge.Factory.newInstance();
		vmergeThree.setVal(STMerge.CONTINUE);
		table.getRow(FOUR).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeThree);
		table.getRow(FOUR).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeThree);

		// Project Caption
		setCaption(ONE, ZERO, PROJECT, table);
		// Set Project Value
		setCellValue(ONE, ONE, projectDetails.getProjectName(), table);
		// Set Date Caption
		setCaption(ONE, TWO, DATE, table);
		// Set Date Value
		String gd = changeDateFortmat(projectDetails.getGeneratedDate());
		setCellValue(TWO, TWO, gd, table);
		// Set Billing Period Caption
		setCaption(ONE, THREE, BILLING_PERIOD, table);
		// Set Billing Period Value
		String padded = String.format("%-40s", " to");
		String splitDate = changeDateFortmat(projectDetails.getFromDate()) + padded
				+ changeDateFortmat(projectDetails.getToDate());
		setCellValue(TWO, THREE, splitDate, table);
		// Set Agreement Number Caption
		setCaption(ONE, FOUR, AGREEMENT_NUMBER, table);
		// Set Agreement NUmber Value
		setCellValue(TWO, FOUR, projectDetails.getAgrmntNumber(), table);
		// Set Responsible Person Caption
		setCaption(THREE, ZERO, RESPONSIBLE_PERSON, table);
		// Set Responsible Person Value
		setCellValue(THREE, ONE, projectDetails.getRespPersonal(), table);
		// Set Order Number Caption
		setCaption(THREE, TWO, ORDER_NUMBER, table);
		// Set Order Number Value
		setCellValue(FOUR, TWO, projectDetails.getOrdrNumber(), table);
		// Set Service Provider Caption
		String SERVICE_PROVIDER = SERVICE.concat(String.format("%-60s", " ")).concat(PROVIDER);
		setCaption(THREE, THREE, SERVICE_PROVIDER, table);
		// Set Service Provider Value
		setCellValue(FOUR, THREE, projectDetails.getSrvcProvider(), table);
		// Set Service Receiver Caption
		setCaption(THREE, FOUR, SERVICE_RECEIVER, table);
		// Set Service Receiver Value
		setCellValue(FOUR, FOUR, projectDetails.getSrvcReceiver(), table);

		for (int i = 5; i <= 10; i++) {
			table.getRow(i).getCell(1).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(4));
			XWPFTableRow rowSix = table.getRow(i);
			int cellSixIndex = rowSix.getTableCells().size() - 1;
			for (int j = cellSixIndex; j >= cellSixIndex - 2; j--) {
				rowSix.getCell(j).getCTTc().newCursor().removeXml();
			}
		}
		// Set Sr Caption
		setCaption(FIVE, ZERO, SR, table);
		// Set Task List Caption
		setCaption(FIVE, ONE, TASK_LIST, table);

		String[] proDescc = projectDetails.getPrjctDesc().split("[.,\n]");
		String[] proDesc = removeEmptyStrings(proDescc);
		int COUNT = 6;
		int srNumber = 1;
		for (String proData : proDesc) {
			setCaption(COUNT, ZERO, srNumber + "", table);
			setCellValue(COUNT, ONE, proData, table);
			table.getRow(COUNT).getCell(ONE).getParagraphArray(0).setAlignment(ParagraphAlignment.LEFT);
			COUNT++;
			srNumber++;
		}

		// Merge
		table.getRow(ELEVEN).getCell(ZERO).setWidth(TWENTY_PER);
		table.getRow(ELEVEN).getCell(ONE).setWidth(FOURTY_PER);
		table.getRow(ELEVEN).getCell(TWO).setWidth(THIRTY_FIVE_PER);
		table.getRow(ELEVEN).getCell(THREE).setWidth(ZERO_PER);
		table.getRow(ELEVEN).getCell(FOUR).setWidth(ZERO_PER);
		mergeCells(ELEVEN, THREE, STMerge.RESTART, table);
		mergeCells(TWELVE, THREE, STMerge.RESTART, table);
		mergeCells(THIRTEEN, THREE, STMerge.RESTART, table);
		mergeCells(FOURTEEN, THREE, STMerge.RESTART, table);

		mergeCells(ELEVEN, FOUR, STMerge.CONTINUE, table);
		mergeCells(TWELVE, FOUR, STMerge.CONTINUE, table);
		mergeCells(THIRTEEN, FOUR, STMerge.CONTINUE, table);
		mergeCells(FOURTEEN, FOUR, STMerge.CONTINUE, table);
		// Set Total Budget Caption
		setCaption(ELEVEN, ONE, TOTAL_BUDGET, table);
		// Set Current Month Budget Caption
		setCaption(ELEVEN, TWO, CURRENT_MONTH_BDGT, table);
		// Set Current Month Budget Value
		setCellValue(TWELVE, TWO, checkZeroValue(projectDetails.getSrvcMonthlyCost()), table);
		// Set Remaining Budget Caption
		setCaption(ELEVEN, THREE, REMAINING_BDGT, table);
		// Set Remaining Budget Value
		setCellValue(TWELVE, THREE, checkZeroValue(projectDetails.getSrvcRemainBdgt()), table);
		// Set Service Cost Caption
		setCaption(TWELVE, ZERO, SERVICE_COST, table);
		// Set Service Cost Value
		setCellValue(TWELVE, ONE, checkZeroValue(projectDetails.getSrvcCost()), table);
		// Set Miscellaneous Caption
		setCaption(THIRTEEN, ZERO, MISCELLANEOUS, table);
		// Set Miscellaneous Value
		setCellValue(THIRTEEN, ONE, checkZeroValue(projectDetails.getMiscCost()), table);
		// Set Miscellaneous Monthly Budget Value
		setCellValue(THIRTEEN, TWO, checkZeroValue(projectDetails.getMiscMonthlyBdgt()), table);
		// Set Miscellaneous Remaining Budget Value
		setCellValue(THIRTEEN, THREE, checkZeroValue(projectDetails.getMiscRemainBdgt()), table);
		// Set Total Caption
		setCaption(FOURTEEN, ZERO, TOTAL, table);
		setCellValue(FOURTEEN, ONE, checkZeroValue(projectDetails.getTotalCost()), table);
		setCellValue(FOURTEEN, TWO, checkZeroValue(projectDetails.getTotalMonthlyBdgt()), table);
		setCellValue(FOURTEEN, THREE, checkZeroValue(projectDetails.getTotalRemainBdgt()), table);

		// Row Number Seventeen is merged into 2 columns
		table.getRow(FIFTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(5));
		XWPFTableRow seventeen = table.getRow(FIFTEEN);
		int cellSeventeenIndex = seventeen.getTableCells().size() - 1;
		for (int i = cellSeventeenIndex; i >= cellSeventeenIndex - 3; i--) {
			seventeen.getCell(i).getCTTc().newCursor().removeXml();
		}

		StringBuilder commonData = new StringBuilder("Billing details : ");
		Set<LevelInfo> levelInfo = projectDetails.getLevelInfo();
		for (LevelInfo lData : levelInfo) {
			if (lData.getLevel() == 1) {
				commonData.append(L1 + lData.getMember() + " (" + lData.getPrice() + ")");
			} else if (lData.getLevel() == 2) {
				commonData.append(L2 + lData.getMember() + " (" + lData.getPrice() + ")");
			} else if (lData.getLevel() == 3) {
				commonData.append(L3 + lData.getMember() + " (" + lData.getPrice() + ")");
			}
		}

		String monthName = getMonthName(projectDetails.getGeneratedDate());
		int year = localDate.getYear();

		String declaration;
		if (projectDetails.getMiscMonthlyBdgt() > 0) {
			declaration = "We hereby declare that the information on this Acceptance Criteria is correct and the invoice for month of "
					+ monthName + " " + year + " will be billed for amount of €" + projectDetails.getTotalMonthlyBdgt()
					+ "."
					+ String.format("%-90s", " ")
							.concat(String.valueOf(commonData).concat(" & €" + projectDetails.getMiscMonthlyBdgt()
									+ " (" + projectDetails.getJustification() + " as Miscellaneous Cost)"));
		} else {
			declaration = "We hereby declare that the information on this Acceptance Criteria is correct and the invoice for month of "
					+ monthName + " will be billed for amount of €" + projectDetails.getTotalMonthlyBdgt() + "."
					+ String.format("%-90s", " ").concat(String.valueOf(commonData));
		}

		// Set Declaration Caption
		setCaption(FIFTEEN, ZERO, declaration, table);

		table.getRow(SIXTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(2));
		table.getRow(SIXTEEN).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(5));

		XWPFTableRow eighteenSix = table.getRow(SIXTEEN);
		int cellTwentyIndex = eighteenSix.getTableCells().size() - 1;
		for (int i = cellTwentyIndex; i >= cellTwentyIndex - 2; i--) {
			eighteenSix.getCell(i).getCTTc().newCursor().removeXml();
		}

		table.getRow(SIXTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(2));
		table.getRow(SIXTEEN).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(5));
		XWPFTableRow twentyRow = table.getRow(SIXTEEN);
		int index = twentyRow.getTableCells().size() - 1;

		table.getRow(SEVENTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(2));
		table.getRow(SEVENTEEN).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(5));
		XWPFTableRow eighteen = table.getRow(SEVENTEEN);
		int eighteenIndex = eighteen.getTableCells().size() - 1;
		for (int i = eighteenIndex; i >= eighteenIndex - 2; i--) {
			eighteen.getCell(i).getCTTc().newCursor().removeXml();
		}
		CTVMerge vmergeContinue = CTVMerge.Factory.newInstance();
		vmergeContinue.setVal(STMerge.CONTINUE);
		for (int i = SIXTEEN; i <= SEVENTEEN; i++) {
			XWPFTableCell continueCell = table.getRow(i).getCell(0);
			XWPFTableCell continueNextCell = table.getRow(i).getCell(1);
			continueCell.getCTTc().addNewTcPr().setVMerge(vmergeContinue);
			continueNextCell.getCTTc().addNewTcPr().setVMerge(vmergeContinue);
		}

		String mngr_name = projectDetails.getMngrName() + " (VWITS)";
		setCellValue(SIXTEEN, ZERO, mngr_name, table);
		setCellValue(SIXTEEN, ONE, projectDetails.getClientName(), table);

		String date = new SimpleDateFormat(PATTERN, Locale.getDefault()).format(new Date());
		FileOutputStream fstream = null;
		try {
			if (projectDetails.getLocation() == null) {
				fstream = new FileOutputStream(ACCEPTANCE_REPORT + "-" + projectDetails.getProjectName() + "-"
						+ localDate.getMonth() + "-" + localDate.getYear() + ".docx");
			} else {
				fstream = new FileOutputStream(
						ACCEPTANCE_REPORT + "-" + projectDetails.getProjectName() + "-" + localDate.getMonth() + "-"
								+ projectDetails.getLocation() + "-" + localDate.getYear() + ".docx");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			accptanceReport.write(fstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fstream;
	}

	private static String checkZeroValue(double costValue) {
		return costValue != 0 ? "€" + costValue : "€---";
	}

	private static String changeDateFortmat(String actualDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(INPUT_FORMAT);
		Date date = null;
		try {
			date = formatter.parse(actualDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(OUTPUT_FORMATE, Locale.getDefault()).format(date);
	}

	private static String getMonthName(String actualDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(INPUT_FORMAT);
		Date date = null;
		try {
			date = formatter.parse(actualDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return new DateFormatSymbols().getMonths()[month];
	}

	public static void addImages(XWPFTable table, int row, int cell, String streamName, String fileName, int emuOne,
			int emuTwo) {
		XWPFParagraph prgrph = table.getRow(row).getCell(cell).addParagraph();
		XWPFRun run = prgrph.createRun();
		try {
			// Load the image as a resource
			Resource resource = new ClassPathResource("static/images/" + streamName);
			InputStream inputStream = resource.getInputStream();
			// Add the picture to the document
			run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, fileName, Units.toEMU(emuOne),
					Units.toEMU(emuTwo));
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] removeEmptyStrings(String[] arr) {
		return Arrays.stream(arr).filter(s -> !s.isEmpty()).toArray(String[]::new);
	}

	public static void setCaption(int rowNum, int cellNum, String caption, XWPFTable table) {
		XWPFParagraph paragraphArray = table.getRow(rowNum).getCell(cellNum).getParagraphArray(0);
		if ((rowNum == SEVENTEEN || rowNum == FIFTEEN) && cellNum == ZERO) {
			paragraphArray.setAlignment(ParagraphAlignment.LEFT);
		} else {
			paragraphArray.setAlignment(ParagraphAlignment.CENTER);
		}
		XWPFRun capProject = paragraphArray.createRun();
		capProject.setBold(true);
		capProject.setText(caption);
		capProject.setFontSize(10);
		capProject.setFontFamily("Arial");

	}

//    public static void setCellValue(int rowNum, int cellNum, String fieldValue, XWPFTable table) {
//        XWPFParagraph para = table.getRow(rowNum).getCell(cellNum).getParagraphArray(0);
//        para.setAlignment(ParagraphAlignment.CENTER);
//        para.createRun().setText(fieldValue);
//    }

	public static void setCellValue(int rowNum, int cellNum, String fieldValue, XWPFTable table) {
		XWPFParagraph para = table.getRow(rowNum).getCell(cellNum).getParagraphArray(0);
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = para.createRun();
		run.setText(fieldValue);
		if (rowNum == SIXTEEN && (cellNum == ZERO || cellNum == ONE)) {
			run.setBold(true);
		}
		run.setFontSize(10); // Font size in points
		run.setFontFamily("Arial");
	}

	public static void mergeCells(int inCell, int mergeCell, STMerge.Enum mergeValue, XWPFTable table) {
		CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
		hMerge1.setVal(mergeValue);
		table.getRow(inCell).getCell(mergeCell).getCTTc().addNewTcPr().setHMerge(hMerge1);
	}

	public static LocalDate dateConvert(String inputDate) {
		SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_FORMAT);
		SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMATE);
		LocalDate formattedDate = null;
		try {
			Date date = inputFormat.parse(inputDate);
			String result = outputFormat.format(date);
			DateTimeFormatter df = DateTimeFormatter.ofPattern(OUTPUT_FORMATE);
			formattedDate = LocalDate.parse(result, df);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	public static void calculateCostPerMonth(ProjectDetails proData, ProjectDetails preRecord) {

		if (preRecord != null) {

			Optional<Double> reduce = proData.getLevelInfo().stream().map(e -> e.getPrice() * e.getMember())
					.collect(Collectors.toList()).stream().reduce(Double::sum);

			double miscMonthlyBdgt = proData.getMiscMonthlyBdgt();
			proData.setSrvcRemainBdgt(preRecord.getSrvcRemainBdgt() - reduce.get());
			proData.setMiscRemainBdgt(preRecord.getMiscRemainBdgt() - miscMonthlyBdgt);
			proData.setTotalMonthlyBdgt(proData.getSrvcMonthlyCost() + proData.getMiscMonthlyBdgt());
			proData.setTotalRemainBdgt(proData.getSrvcRemainBdgt() + proData.getMiscRemainBdgt());

		} else if (Utilities.dateConvert(proData.getGeneratedDate()).getMonthValue() == 1) {

			Optional<Double> reduce = proData.getLevelInfo().stream().map(e -> e.getPrice() * e.getMember())
					.collect(Collectors.toList()).stream().reduce(Double::sum);

			proData.setSrvcMonthlyCost(reduce.get());
			proData.setSrvcRemainBdgt(proData.getSrvcCost() - reduce.get());

			proData.setMiscMonthlyBdgt(proData.getMiscPricing());
			proData.setMiscRemainBdgt(proData.getMiscCost() - proData.getMiscMonthlyBdgt());

			proData.setTotalMonthlyBdgt(proData.getSrvcMonthlyCost() + proData.getMiscMonthlyBdgt());
			proData.setTotalRemainBdgt(proData.getSrvcRemainBdgt() + proData.getMiscRemainBdgt());
		} else {

			Optional<Double> reduce = proData.getLevelInfo().stream().map(e -> e.getPrice() * e.getMember())
					.collect(Collectors.toList()).stream().reduce(Double::sum);
			proData.setSrvcRemainBdgt(proData.getSrvcCost() - reduce.get());
			proData.setMiscRemainBdgt(proData.getMiscCost() - proData.getMiscMonthlyBdgt());

			proData.setTotalMonthlyBdgt(proData.getSrvcMonthlyCost() + proData.getMiscMonthlyBdgt());
			proData.setTotalRemainBdgt(proData.getSrvcRemainBdgt() + proData.getMiscRemainBdgt());
		}
	}

	public static void prepairePersistanceData(ProjectDetails proDetails, ProjectDetails proPersist) {

		proPersist.setAgrmntNumber(proDetails.getAgrmntNumber());
		proPersist.setBrand(proDetails.getBrand());
		proPersist.setDepartment(proDetails.getDepartment());
		proPersist.setSubDeprtmt(proDetails.getSubDeprtmt());
		proPersist.setProjectName(proDetails.getProjectName());
		proPersist.setGeneratedDate(proDetails.getGeneratedDate());
		proPersist.setFromDate(proDetails.getFromDate());
		proPersist.setToDate(proDetails.getToDate());
		proPersist.setOrdrNumber(proDetails.getOrdrNumber());
		proPersist.setRespPersonal(proDetails.getRespPersonal());
		proPersist.setSrvcProvider(proDetails.getSrvcProvider());
		proPersist.setSrvcReceiver(proDetails.getSrvcReceiver());
		proPersist.setPrjctDesc(proDetails.getPrjctDesc());
		proPersist.setLocation(proDetails.getLocation());

		// CostDetails
		proPersist.setSrvcCost(proDetails.getSrvcCost());
		proPersist.setSrvcMonthlyCost(proDetails.getSrvcMonthlyCost());
		proPersist.setSrvcRemainBdgt(proDetails.getSrvcRemainBdgt());
		proPersist.setMiscCost(proDetails.getMiscCost());
		proPersist.setMiscMonthlyBdgt(proDetails.getMiscMonthlyBdgt());
		proPersist.setMiscRemainBdgt(proDetails.getMiscRemainBdgt());
		proPersist.setTotalCost(proDetails.getTotalCost());
		proPersist.setTotalMonthlyBdgt(proDetails.getTotalMonthlyBdgt());
		proPersist.setTotalRemainBdgt(proDetails.getTotalRemainBdgt());
		proPersist.setMiscPricing(proDetails.getMiscPricing());
		proPersist.setMngrName(proDetails.getMngrName());
		proPersist.setClientName(proDetails.getClientName());
		proPersist.setJustification(proDetails.getJustification());
		prepaireLevelInfo(proPersist, proDetails);
		prepaireCostInfo(proPersist, proDetails);
		prepaireRemInfo(proPersist, proDetails);
	}

	private static void prepaireLevelInfo(ProjectDetails proPersist, ProjectDetails proDetails) {
		Set<LevelInfo> levelSet = new HashSet<>();
		for (LevelInfo levelInfo : proDetails.getLevelInfo()) {
			LevelInfo levelData = new LevelInfo();
			levelData.setLevel(levelInfo.getLevel());
			levelData.setMember(levelInfo.getMember());
			levelData.setPrice(levelInfo.getPrice());
			levelData.setLocation(proDetails.getLocation());
			levelSet.add(levelData);
		}
		proPersist.setLevelInfo(levelSet);
	}

	private static void prepaireCostInfo(ProjectDetails proPersist, ProjectDetails proDetails) {
		CostDetails costDetails = new CostDetails();
		costDetails.setSrvcCost(proDetails.getSrvcCost());
		costDetails.setSrvcMonthlyCost(proDetails.getSrvcMonthlyCost());
		costDetails.setSrvcRemainBdgt(proDetails.getSrvcRemainBdgt());
		costDetails.setMiscCost(proDetails.getMiscCost());
		costDetails.setMiscMonthlyBdgt(proDetails.getMiscMonthlyBdgt());
		costDetails.setMiscRemainBdgt(proDetails.getMiscRemainBdgt());
		costDetails.setMiscPricing(proDetails.getMiscPricing());
		costDetails.setTotalCost(proDetails.getTotalCost());
		costDetails.setTotalMonthlyBdgt(proDetails.getTotalMonthlyBdgt());
		costDetails.setTotalRemainBdgt(proDetails.getTotalRemainBdgt());
		costDetails.setAgrmntNumber(proDetails.getAgrmntNumber());
		costDetails.setFromDate(proDetails.getFromDate());
		costDetails.setLocation(proDetails.getLocation());
		proPersist.setCostDetails(costDetails);
	}

	private static void prepaireRemInfo(ProjectDetails proPersist, ProjectDetails proDetails) {

		if (proDetails.getSrvcCost() - proDetails.getSrvcMonthlyCost() == proDetails.getSrvcRemainBdgt()) {
			RemainingData remData = new RemainingData();
			remData.setSrvcRemainBdgt(proDetails.getSrvcRemainBdgt());
			remData.setMiscRemainBdgt(proDetails.getMiscRemainBdgt());
			remData.setTotalRemainBdgt(proDetails.getTotalRemainBdgt());
			remData.setAgrmntNumber(proDetails.getAgrmntNumber());

			proPersist.setRemainingData(remData);
		}
	}

}
