package vw.controller;

import com.spire.doc.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AspireFile {

    public static void main(String[] args) throws FileNotFoundException {
        Document document = new Document();
       // document.loadFromFile("acceptance-report.docx");
        Section section = document.addSection();
        Table table = section.addTable(true);
        table.resetCells(1,5);


        TableCell rowZeroCellZero = table.getRows().get(0).getCells().get(0);
        FileInputStream fis = new FileInputStream("img_11.png");
        rowZeroCellZero.addParagraph().appendPicture(fis).setHeight(50);
        rowZeroCellZero.setWidth(50);

        TableCell rowZeroCellOne = table.getRows().get(0).getCells().get(1);
        FileInputStream fis2 = new FileInputStream("img_12.png");
        rowZeroCellOne.addParagraph().appendPicture(fis2).setHeight(50);
        rowZeroCellOne.setWidth(100);

        TableCell rowZeroCellTwo = table.getRows().get(0).getCells().get(4);
        FileInputStream fis3 = new FileInputStream("img11.png");
        rowZeroCellTwo.addParagraph().appendPicture(fis3).setHeight(50);
        rowZeroCellTwo.setWidth(50);
        table.applyHorizontalMerge(0,2,4);

        Table tableTwo = section.addTable(true);
        tableTwo.resetCells(10,5);

        tableTwo.get(0,0).setWidth(40);
        tableTwo.get(0,1).setWidth(40);



        /*table.applyVerticalMerge(0, 1, 2);
        table.applyVerticalMerge(1, 1, 2);

       *//* table.get(1,1).getCellFormat().set*//*

        table.applyVerticalMerge(0, 3, 4);
        table.applyVerticalMerge(1, 3, 4);
    */

       /* TableCell tableCell = table.get(1, 1);
        tableCell.splitCell(5,1);*/

        System.out.println("Report Generated...");
        //document.saveToFile("report", FileFormat.Docx);

        document.saveToFile("data.docx",FileFormat.Docx);
    }
}
