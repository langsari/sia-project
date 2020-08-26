package com.sia.main.web.json_model;

public class RoleMenu {
	
	private String roleId;
	
	private String[] roleMenus;
	
	public RoleMenu() {
		
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String[] getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(String[] roleMenus) {
		this.roleMenus = roleMenus;
	}
	
}
