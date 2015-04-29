package com.javaschool.ivanov.Filters;


import com.javaschool.ivanov.Beans.UserBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestFilter extends AbstractFilter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object object = ((HttpServletRequest) servletRequest).getSession().getAttribute("userBean");
        if(object == null)
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(((UserBean) object).getAcceessLevel()== MANAGER_STATUS)
            {
                ((HttpServletResponse) servletResponse).sendRedirect("manager_page.xhtml?faces-redirect=true");
            }
            else
            {
                filterChain.doFilter(servletRequest, servletResponse);
            }
    }
}
