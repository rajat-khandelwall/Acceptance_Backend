package vw.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.xml.parsers.DocumentBuilder;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlToJava {
    public static void main(String[] args) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<title> ACCEPTANCE TABLE </title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<table border=\"1\">\n");
        html.append("<tr>\n");
        html.append("<td>row_1/col_1</td>\n");
        html.append("<td>col_2</td>\n");
        html.append("<td>col_3</td>\n");
        html.append("<td>col_4</td>\n");
        html.append("<td>col_5</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>row_2</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>row_3</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>row_4</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("td></td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>row_5</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<td>row_6</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<td>row_7</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<td>row_8</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<td>row_9</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("<td>row_10</td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("<td></td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("</body>\n");
        html.append("</html>\n");

       /* FileOutputStream fos = new FileOutputStream("AP.docx");
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(html.toString());
        doc.write(fos);
        fos.close();

*/
        //BufferedWriter writer = new BufferedWriter()
        System.out.println("File has been created....");
    }
}
