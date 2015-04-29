package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Services.PdfService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;


@SessionScoped
@ManagedBean
public class ReportBean implements Serializable{

    private Date date1;
    private Date date2;
    private String pdf;

    @EJB
    private PdfService pdfService;




    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void findReport()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
        pdfService.createPDF(response, date1, date2);
        context.responseComplete();
    }
}
