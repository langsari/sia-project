package com.sia.main.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class WebAppInitializer implements WebApplicationInitializer { 
	
	@Override
	public void onStartup(ServletContext servletContext)throws ServletException {
		SIAMainWebApplication webApplication = SIAMainWebApplication.getInstance();
		webApplication.setServletContext(servletContext);
		webApplication.init();
	}

}