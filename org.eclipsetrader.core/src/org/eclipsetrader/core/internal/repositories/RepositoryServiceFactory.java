package org.eclipsetrader.core.internal.repositories;

import org.eclipsetrader.core.repositories.IRepositoryService;
import org.eclipsetrader.core.repositories.IRepositoryServiceFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

public class RepositoryServiceFactory implements IRepositoryServiceFactory {

	RepositoryService serviceInstance;
	
	public void dispose() {
		if( serviceInstance != null ) {
			serviceInstance.shutDown();
			serviceInstance = null;
		}
	}

	@Override
	public IRepositoryService getService(Bundle bundle,
			ServiceRegistration<IRepositoryService> registration) {
		// TODO Auto-generated method stub
		if( serviceInstance == null ) {
			serviceInstance = new RepositoryService();
			serviceInstance.startUp();
		}
		return serviceInstance;
	}

	@Override
	public void ungetService(Bundle bundle,
			ServiceRegistration<IRepositoryService> registration,
			IRepositoryService service) {
		// TODO Auto-generated method stub
		
	}

}
