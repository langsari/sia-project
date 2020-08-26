package com.sia.main.web.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ServiceLocator<T> {
	
	private ServiceTracker serviceTracker;
	
	private Class<T> serviceClass;
	
	public ServiceLocator() {
	}
	
	public ServiceLocator(BundleContext bundleContext, Class<T> serviceClass) {
		this.serviceClass = serviceClass;
		this.serviceTracker = new ServiceTracker(bundleContext, this.serviceClass.getName(), null);
		this.serviceTracker.open();
	}
	
	public T getService(long timeout) {
		T service = null;
		try {
			service = this.serviceClass.cast(timeout == -1 ? this.serviceTracker.getService() : this.serviceTracker.waitForService(timeout));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return service;
	}
	
	public void close() {
		this.serviceTracker.close();
	}

}