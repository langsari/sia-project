package com.sia.main.service.module;

import org.osgi.framework.Bundle;

public enum OSGiBundleStatus {
	
	ACTIVE(Bundle.ACTIVE),
	STARTING(Bundle.STARTING),
	STOPPING(Bundle.STOPPING),
	RESOLVED(Bundle.RESOLVED),
	INSTALLED(Bundle.INSTALLED),
	UNINSTALLED(Bundle.UNINSTALLED);
	
	private int frameWorkStatus;
	
	private OSGiBundleStatus(int status) {
		this.frameWorkStatus = status;
	}	
	
	public int getFrameworkStatus() {
		return this.frameWorkStatus;
	}
	
	public static OSGiBundleStatus valueOf(int status) throws IllegalArgumentException {
		for(OSGiBundleStatus osgiBundleStatus : values()) {
			if(osgiBundleStatus.getFrameworkStatus() == status) {
				return osgiBundleStatus;
			}
		}
		throw new IllegalArgumentException("No enum constant " + OSGiBundleStatus.class + "." + status);
	}
	
}
