package com.javaschool.ivanov.Filters;


import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class AbstractFilter implements Filter{

    protected static final int MANAGER_STATUS = 3;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
