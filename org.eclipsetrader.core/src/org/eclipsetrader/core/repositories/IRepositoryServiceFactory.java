package org.eclipsetrader.core.repositories;

import org.eclipsetrader.core.internal.repositories.RepositoryService;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

public interface IRepositoryServiceFactory extends ServiceFactory<IRepositoryService>{

}