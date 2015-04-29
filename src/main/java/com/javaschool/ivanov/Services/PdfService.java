package com.javaschool.ivanov.Services;



import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.javaschool.ivanov.DTO.ReportInfo;
import com.javaschool.ivanov.DTO.TicketInfo;
import com.javaschool.ivanov.WebServices.ReportRSClient;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class PdfService {

    @EJB
    private ReportRSClient reportRSClient;

    public void createPDF(HttpServletResponse response, Date date1, Date date2)
    {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition",  "inline=filename=file.pdf");

        ReportInfo reportInfo = reportRSClient.findReport(date1, date2);
        try {
            Document document = new Document();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();

            BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(bf, 18, Font.BOLD, new CMYKColor(100, 91, 7, 32));
            Font contextFont = new Font(bf, 14, Font.NORMAL);
            Font tableHeaderFont = new Font(bf, 10, Font.NORMAL);
            Font tableFont = new Font(bf, 8, Font.NORMAL);


            Paragraph title = new Paragraph("Отчет о купленных билетах", titleFont);
            title.setSpacingBefore(55);
            title.setSpacingAfter(25);
            document.add(title);

            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            Paragraph context = new Paragraph("С " + df.format(date1) + " по " + df.format(date2) + " " +
                    "было куплено " + reportInfo.size() + " билет(а).", contextFont);
            document.add(context);

        PdfPTable t = new PdfPTable(8);
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);

        PdfPCell c1 = new PdfPCell(new Phrase("Поезд", tableHeaderFont));
        t.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase("Маршрут", tableHeaderFont));
        t.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Дата", tableHeaderFont));
        t.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Фамилия", tableHeaderFont));
        t.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Имя", tableHeaderFont));
        t.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Дата рождения", tableHeaderFont));
        t.addCell(c6);

        PdfPCell c7 = new PdfPCell(new Phrase("От", tableHeaderFont));
        t.addCell(c7);

        PdfPCell c8 = new PdfPCell(new Phrase("До", tableHeaderFont));
        t.addCell(c8);


        for(int i = 0; i < reportInfo.size(); i++)
        {
            TicketInfo ticketInfo = reportInfo.get(i);
            String trainName = ticketInfo.getTrainName();
            Paragraph p1 = new Paragraph( trainName, tableFont);
            t.addCell(p1);

            String routeName = ticketInfo.getRouteName();
            Paragraph p2 = new Paragraph( routeName, tableFont);
            t.addCell(p2);

            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp departureDate = ticketInfo.getDepartureDate();
            Paragraph p3 = new Paragraph( df2.format(departureDate), tableFont);
            t.addCell(p3);

            String lastName = ticketInfo.getLastName();
            Paragraph p4 = new Paragraph( lastName, tableFont);
            t.addCell(p4);

            String firstName = ticketInfo.getFirstName();
            Paragraph p5 = new Paragraph( firstName, tableFont);
            t.addCell(p5);

            java.sql.Date birthday = ticketInfo.getBirthday();
            Paragraph p6 = new Paragraph( df.format(birthday), tableFont);
            t.addCell(p6);

            String departureStation = ticketInfo.getDepartureStation();
            Paragraph p7 = new Paragraph( departureStation, tableFont);
            t.addCell(p7);

            String arrivalStation = ticketInfo.getArrivalStation();
            Paragraph p8 = new Paragraph( arrivalStation, tableFont);
            t.addCell(p8);

        }

        document.add(t);
            document.close();

            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        }
        catch(DocumentException e) {

        }
        catch (IOException e) {

        }
    }

}
