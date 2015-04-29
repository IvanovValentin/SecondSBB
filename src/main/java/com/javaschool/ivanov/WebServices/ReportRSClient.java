package com.javaschool.ivanov.WebServices;


import com.javaschool.ivanov.DTO.ReportInfo;
import com.javaschool.ivanov.DTO.TicketInfo;

import javax.ejb.Stateless;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Stateless
public class ReportRSClient {

    public ReportInfo findReport(Date date1, Date date2){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/sbb-1.0-SNAPSHOT/rest/getReport");
        Invocation invocation = webTarget.queryParam("date1", date1).queryParam("date2", date2).request().buildGet();
        Response response = invocation.invoke();
            return response.readEntity(ReportInfo.class);
    }

}


