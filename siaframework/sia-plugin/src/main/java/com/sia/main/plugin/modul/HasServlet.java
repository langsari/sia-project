package com.sia.main.plugin.modul;

import java.util.List;

public interface HasServlet {

	public void setUrlMapping(String urlMapping);
	
	public String getUrlMapping();
	
	public void setServletConfigLocations(List<String> servletConfigLocations);
	
	public void addServletConfigLocation(String servletConfigLocation);
	
	public List<String> getServletConfigLocations();
	
	public void setServletName(String servletName);
	
	public String getServletName();
	
}
