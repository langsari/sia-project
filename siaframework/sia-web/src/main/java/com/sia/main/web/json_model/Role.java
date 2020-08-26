package com.sia.main.web.json_model;

import java.util.UUID;

public class Role {

	private UUID roleId;
	
	private String roleName;

	public Role() {
		
	}

	public Role(UUID roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
