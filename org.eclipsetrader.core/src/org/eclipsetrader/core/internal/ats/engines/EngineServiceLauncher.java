package org.eclipsetrader.core.internal.ats.engines;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipsetrader.core.ILauncher;
import org.eclipsetrader.core.ats.engines.IEngineFactory;
import org.eclipsetrader.core.internal.CoreActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class EngineServiceLauncher implements ILauncher, IExecutableExtension {

	private String id;
	private String name;

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		id = config.getAttribute("id");
		name = config.getAttribute("name");
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void launch(IProgressMonitor monitor) {
		try {
			BundleContext context = CoreActivator.getDefault().getBundle()
					.getBundleContext();
			ServiceReference<EngineService> serviceReference = context
					.getServiceReference(EngineService.class);
			if (serviceReference != null) {
				EngineService service = context.getService(serviceReference);

				IEngineFactory[] engines = service.getEngines();

				context.ungetService(serviceReference);
			}
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, CoreActivator.PLUGIN_ID,
					0, "Error reading engine service", e);
			CoreActivator.getDefault().getLog().log(status);
		}
	}

	@Override
	public void terminate(IProgressMonitor monitor) {
		try {
			BundleContext context = CoreActivator.getDefault().getBundle()
					.getBundleContext();
			ServiceReference<EngineService> serviceReference = context
					.getServiceReference(EngineService.class);
			if (serviceReference != null) {
				EngineService service = context.getService(serviceReference);

				IEngineFactory[] connector = service.getEngines();

				context.ungetService(serviceReference);
			}
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, CoreActivator.PLUGIN_ID,
					0, "Error reading engine service", e);
			CoreActivator.getDefault().getLog().log(status);
		}
	}

}
