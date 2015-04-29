package com.javaschool.ivanov.WebServices;


import com.javaschool.ivanov.DTO.UserInfo;
import com.javaschool.ivanov.Exception.IncorrectDataException;

import javax.ejb.Stateless;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class AuthorizationRSClient {

    public int authorization(UserInfo userInfo) throws IncorrectDataException {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/sbb-1.0-SNAPSHOT/rest/authorization");
        Invocation invocation = webTarget.request().buildPost(Entity.entity(userInfo, MediaType.APPLICATION_JSON));
        Response response = invocation.invoke();
        if (response.getStatusInfo() == Response.Status.OK) {
            Integer accessLevel = response.readEntity(Integer.class);
            return accessLevel;
        } else throw new IncorrectDataException();
    }
}
