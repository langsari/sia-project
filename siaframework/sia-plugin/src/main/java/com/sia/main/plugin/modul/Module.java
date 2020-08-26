package com.sia.main.plugin.modul;

import java.util.UUID;

public interface Module extends Plugin, HasMenu {
	
	public void setModuleId(UUID moduleId);
	
	public UUID getModuleId();
	
	public void setModuleName(String moduleName);
	
	public String getModuleName();

}
