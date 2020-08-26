package com.sia.main.plugin.modul.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sia.main.plugin.modul.HasServlet;
import com.sia.main.plugin.modul.Menu;
import com.sia.main.plugin.modul.Module;

public class ServletBasedModule implements Module, HasServlet {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String pluginName;
	
	private String pluginVersion;
	
	private List<Menu> menus;
	
	private UUID moduleId;
	
	private String moduleName;
	
	private String urlMapping;
	
	private List<String> servletConfigLocations;
	
	public ServletBasedModule() {
		this.menus = new ArrayList<Menu>();
		this.servletConfigLocations = new ArrayList<String>();
		this.moduleId = UUID.randomUUID();
	}
	
	public ServletBasedModule(String pluginName, String pluginVersion,
			List<Menu> menus, String moduleName, String urlMapping,
			List<String> servletConfigLocations) {
		this();
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.menus = menus;
		this.moduleName = moduleName;
		this.setUrlMapping(urlMapping);
		this.servletConfigLocations = servletConfigLocations;
	}

	@Override
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	@Override
	public String getPluginName() {
		return this.pluginName;
	}

	@Override
	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	@Override
	public String getPluginVersion() {
		return this.pluginVersion;
	}

	@Override
	public void addMenu(Menu menu) {
		if(this.menus == null)
			this.menus = new ArrayList<Menu>();
		this.menus.add(menu);
	}
	
	@Override
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public List<Menu> getMenus() {
		return this.menus;
	}

	@Override
	public void setModuleId(UUID moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public UUID getModuleId() {
		return this.moduleId;
	}
	
	@Override
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String getModuleName() {
		return this.moduleName;
	}

	@Override
	public void setUrlMapping(String urlMapping) {
		this.urlMapping = this.standardizeUrlMapping(urlMapping);
	}

	@Override
	public String getUrlMapping() {
		return this.urlMapping;
	}
	
	@Override
	public void setServletName(String servletName) {
		if(this.logger.isInfoEnabled())
			this.logger.info("servlet name will be generated from module name");
	}
	
	@Override
	public String getServletName() {
		if(this.moduleName != null && !this.moduleName.equals("")) {
			StringBuilder servletName = new StringBuilder();
			int i = 0;
			String[] moduleName = this.getModuleName().toLowerCase().split(" ");
			for(String string: moduleName) {
				if(i > 0) servletName.append('-');
				servletName.append(string);
				i++;
			}
			servletName.append("-servlet");
			return servletName.toString();
		} else {
			return null;
		}
	}
	
	@Override
	public void setServletConfigLocations(List<String> servletConfigLocations) {
		this.servletConfigLocations = servletConfigLocations;
	}

	@Override
	public void addServletConfigLocation(String servletConfigLocation) {
		if(this.servletConfigLocations == null) 
			this.servletConfigLocations = new ArrayList<String>();
		this.servletConfigLocations.add(servletConfigLocation);
	}

	@Override
	public List<String> getServletConfigLocations() {
		return this.servletConfigLocations;
	}
	
	private String standardizeUrlMapping(String urlMapping) {
		if(urlMapping != null && !urlMapping.equals("")) {
			StringBuilder result = new StringBuilder();
			result.append("/modul");
			if(urlMapping.charAt(0) != '/') {
				result.append('/');
			}
			if(urlMapping.charAt(urlMapping.length()-1) != '*') {
				if(urlMapping.charAt(urlMapping.length()-1) != '/') {
					result.append(urlMapping + "/*");
				} else {
					result.append(urlMapping + "*");
				}
			} else {
				result.append(urlMapping);
			}
			return result.toString();
		} else {
			return null;
		}
	}

}
