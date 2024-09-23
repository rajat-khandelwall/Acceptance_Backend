package vw.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;

public class WordFile {
    public static void main(String[] args) throws IOException, InvalidFormatException {

        XWPFDocument accptanceReport = new XWPFDocument();
        XWPFTable table = accptanceReport.createTable(19, 5);
        XWPFParagraph p1 = table.getRow(0).getCell(0).addParagraph();
        XWPFRun r1 = p1.createRun();
        FileInputStream fis = new FileInputStream("img_12.png");
        r1.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, "img_1", Units.pixelToEMU(75),
                Units.pixelToEMU(75));

        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(3));
        XWPFParagraph p2 = table.getRow(0).getCell(1).addParagraph();
        XWPFRun r2 = p2.createRun();
        FileInputStream fis2 = new FileInputStream("img_22.png");
        r2.addPicture(fis2, XWPFDocument.PICTURE_TYPE_PNG, "img_2", Units.pixelToEMU(300),
                Units.pixelToEMU(75));

        XWPFParagraph p3 = table.getRow(0).getCell(2).addParagraph();
        XWPFRun r3 = p3.createRun();
        FileInputStream fis3 = new FileInputStream("img22.png");
        r3.addPicture(fis3, XWPFDocument.PICTURE_TYPE_PNG, "img", Units.pixelToEMU(75),
                Units.pixelToEMU(75));
        XWPFTableRow row = table.getRow(0);
        int cellIndex = row.getTableCells().size() - 1;
        for (int i = cellIndex; i >= cellIndex - 1; i--) {
            row.getCell(i).getCTTc().newCursor().removeXml();
        }
        table.setTableAlignment(TableRowAlign.CENTER);

        CTVMerge vmerge = CTVMerge.Factory.newInstance();
        vmerge.setVal(STMerge.RESTART);
        table.getRow(1).getCell(0).getCTTc().addNewTcPr().setVMerge(vmerge);
        table.getRow(1).getCell(1).getCTTc().addNewTcPr().setVMerge(vmerge);

        // Secound Row cell will be merged
        CTVMerge vmerge1 = CTVMerge.Factory.newInstance();
        vmerge1.setVal(STMerge.CONTINUE);
        table.getRow(2).getCell(0).getCTTc().addNewTcPr().setVMerge(vmerge1);
        table.getRow(2).getCell(1).getCTTc().addNewTcPr().setVMerge(vmerge1);


        CTVMerge vmergeTwo = CTVMerge.Factory.newInstance();
        vmergeTwo.setVal(STMerge.RESTART);
        table.getRow(3).getCell(0).getCTTc().addNewTcPr().setVMerge(vmergeTwo);
        table.getRow(3).getCell(1).getCTTc().addNewTcPr().setVMerge(vmergeTwo);

        // Secound Row cell will be merged
        CTVMerge vmergeThree = CTVMerge.Factory.newInstance();
        vmergeThree.setVal(STMerge.CONTINUE);
        table.getRow(4).getCell(0).getCTTc().addNewTcPr().setVMerge(vmergeThree);
        table.getRow(4).getCell(1).getCTTc().addNewTcPr().setVMerge(vmergeThree);


        XWPFParagraph paragraphArray = table.getRow(1).getCell(0).getParagraphArray(0);
        paragraphArray.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capProject = paragraphArray.createRun();
        capProject.setBold(true);
        capProject.setText("Project");

        XWPFParagraph para = table.getRow(1).getCell(1).getParagraphArray(0);
        para.setAlignment(ParagraphAlignment.CENTER);
        para.createRun().setText("Stargate");

        XWPFParagraph datePara = table.getRow(1).getCell(2).getParagraphArray(0);
        datePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capDate = datePara.createRun();
        capDate.setBold(true);
        capDate.setText("Date");

        /*XWPFParagraph dateValuePara = table.getRow(2).getCell(2).getParagraphArray(0);
        dateValuePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capDateValue = dateValuePara.createRun();
        capDateValue.setBold(true);
        capDateValue.setText(String.valueOf(new Date()));*/

        XWPFParagraph billingPara = table.getRow(1).getCell(3).getParagraphArray(0);
        billingPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capBilling = billingPara.createRun();
        capBilling.setBold(true);
        capBilling.setText("Billing Period");

        XWPFParagraph agreePara = table.getRow(1).getCell(4).getParagraphArray(0);
        agreePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capAgree = agreePara.createRun();
        capAgree.setBold(true);
        capAgree.setText("Agreement Number");

        XWPFParagraph paraPrsn = table.getRow(3).getCell(0).getParagraphArray(0);
        paraPrsn.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capPrsn = paraPrsn.createRun();
        capPrsn.setBold(true);
        capPrsn.setText("Responsible Person");

        XWPFParagraph ordrPara = table.getRow(3).getCell(2).getParagraphArray(0);
        ordrPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capOrdr = ordrPara.createRun();
        capOrdr.setBold(true);
        capOrdr.setText("Order Number");

        XWPFParagraph srvcPara = table.getRow(3).getCell(3).getParagraphArray(0);
        srvcPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capSrvc = srvcPara.createRun();
        capSrvc.setBold(true);
        capSrvc.setText("Service Provider");


        XWPFParagraph rcvPara = table.getRow(3).getCell(4).getParagraphArray(0);
        rcvPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capRcv = rcvPara.createRun();
        capRcv.setBold(true);
        capRcv.setText("Service Receiver");

        /*table.getRow(6).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(4));*/

       /* XWPFTableRow rowSix = table.getRow(6);
        int cellSixIndex = rowSix.getTableCells().size() - 1;
        for (int i = cellSixIndex; i >= cellSixIndex-2; i--) {
            rowSix.getCell(i).getCTTc().newCursor().removeXml();
        }*/

        for (int i = 5; i <= 12; i++) {
            table.getRow(i).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                    .setVal(BigInteger.valueOf(4));
            XWPFTableRow rowSix = table.getRow(i);
            int cellSixIndex = rowSix.getTableCells().size() - 1;
            for (int j = cellSixIndex; j >= cellSixIndex - 2; j--) {
                rowSix.getCell(j).getCTTc().newCursor().removeXml();
            }
        }


        XWPFParagraph srNoPara = table.getRow(5).getCell(0).getParagraphArray(0);
        //srNoPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capSrNo = srNoPara.createRun();
        capSrNo.setBold(true);
        capSrNo.setText("Sr.");

        XWPFParagraph taskPara = table.getRow(5).getCell(1).getParagraphArray(0);
        //taskPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capTask = taskPara.createRun();
        capTask.setBold(true);
        capTask.setText("Task List");

        XWPFParagraph sevenRowPara = table.getRow(6).getCell(0).getParagraphArray(0);
        sevenRowPara.createRun().setText("1");
        XWPFParagraph eightRowPara = table.getRow(7).getCell(0).getParagraphArray(0);
        eightRowPara.createRun().setText("2");
        XWPFParagraph nineRowPara = table.getRow(8).getCell(0).getParagraphArray(0);
        nineRowPara.createRun().setText("3");

     /*   table.getRow(15).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(1));*/
        /*XWPFTableRow rowFifteen = table.getRow(15);
        int cellFifteenIndex = rowFifteen.getTableCells().size() - 1;
        System.out.println("SIze "+cellFifteenIndex);
        for (int i = cellFifteenIndex; i >= cellFifteenIndex; i--) {
            rowFifteen.getCell(i).getCTTc().newCursor().removeXml();
        }*/

        /*//table.getRow(15).getCell(0).getCTTc().addNewTcPr().newCursor().removeXml();

        table.getRow(15).removeCell(4);*/

        XWPFParagraph totBdgtPara = table.getRow(13).getCell(1).getParagraphArray(0);
        totBdgtPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capTotBdgt = totBdgtPara.createRun();
        capTotBdgt.setBold(true);
        capTotBdgt.setText("Total Budget");

        XWPFParagraph totMonthBdgtPara = table.getRow(13).getCell(2).getParagraphArray(0);
        totMonthBdgtPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capMonthTotBdgt = totMonthBdgtPara.createRun();
        capMonthTotBdgt.setBold(true);
        capMonthTotBdgt.setText("Current Month Budget");

        XWPFParagraph remBdgtPara = table.getRow(13).getCell(3).getParagraphArray(0);
        remBdgtPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capRemBdgt = remBdgtPara.createRun();
        capRemBdgt.setBold(true);
        capRemBdgt.setText("Remaining Budget");

        XWPFParagraph srcCostPara = table.getRow(14).getCell(0).getParagraphArray(0);
        srcCostPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capSrvcCost = srcCostPara.createRun();
        capSrvcCost.setBold(true);
        capSrvcCost.setText("Service cost");

        XWPFParagraph misCostPara = table.getRow(15).getCell(0).getParagraphArray(0);
        misCostPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capMisCost = misCostPara.createRun();
        capMisCost.setBold(true);
        capMisCost.setText("Miscellaneous");

        XWPFParagraph totalPara = table.getRow(16).getCell(0).getParagraphArray(0);
        totalPara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capTotal = totalPara.createRun();
        capTotal.setBold(true);
        capTotal.setText("Total");


        table.getRow(17).getCell(0).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(5));
        XWPFTableRow ninteen = table.getRow(17);
        int cellNinteenIndex = ninteen.getTableCells().size() - 1;
        for (int i = cellNinteenIndex; i >= cellNinteenIndex - 3; i--) {
            ninteen.getCell(i).getCTTc().newCursor().removeXml();
        }

        XWPFParagraph declarePara = table.getRow(17).getCell(0).getParagraphArray(0);
        declarePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun capDeclare = declarePara.createRun();
        capDeclare.setBold(true);
        capDeclare.setText("We hereby  declare that the information on this Acceptance Criteria is correct and the invoice for month of November 2023 will be billed for amount of € XXX.\n" +
                "Billing details : L2 :2.5 (XXX), L2:2 (XXXX), L2:2(XXXX)");


        table.getRow(18).getCell(0).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(2));

        table.getRow(18).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(5));


        XWPFTableRow ninteenSix = table.getRow(18);
        int cellTwentyIndex = ninteenSix.getTableCells().size() - 1;
        for (int i = cellTwentyIndex; i >= cellTwentyIndex - 2; i--) {
            ninteenSix.getCell(i).getCTTc().newCursor().removeXml();
        }
//Service cost

        FileOutputStream fstream = new FileOutputStream("test.docx");
        accptanceReport.write(fstream);
        System.out.println("report has been generated...");

    }
}

