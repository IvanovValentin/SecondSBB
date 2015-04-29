package com.javaschool.ivanov.Beans;



import com.javaschool.ivanov.DTO.UserInfo;
import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.WebServices.AuthorizationRSClient;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "authorizationBean")
public class AuthorizationBean implements Serializable {

    private String login = null;
    private String password = null;
    private int accessLevel = 0;
    private static final int MANAGER_STATUS = 3;


    @ManagedProperty( value = "#{userBean}")
    private UserBean userBean;

    @EJB
    private AuthorizationRSClient authorizationRSClient;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5.getHash(password);
    }

    public String authorization()
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setLogin(login);
        userInfo.setPassword(password);
        try {
            accessLevel = authorizationRSClient.authorization(userInfo);
            userBean.setLogin(login);
            userBean.setAcceessLevel(accessLevel);
        }
        catch (IncorrectDataException e)
        {
            return "index_page?faces-redirect=true";
        }
        if(accessLevel == MANAGER_STATUS) return  "manager_page.xhtml?faces-redirect=true";
        else return  "index_page.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index_page.xhtml?faces-redirect=true";
    }
}
