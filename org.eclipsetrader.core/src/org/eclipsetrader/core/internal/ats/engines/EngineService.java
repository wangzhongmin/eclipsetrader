package org.eclipsetrader.core.internal.ats.engines;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipsetrader.core.ats.IStrategy;
import org.eclipsetrader.core.ats.engines.BaseEngine;
import org.eclipsetrader.core.ats.engines.IEngineFactory;
import org.eclipsetrader.core.ats.engines.IEngineService;
import org.eclipsetrader.core.internal.CoreActivator;

public class EngineService implements IEngineService {

	private static final String EXTENSION_ID = "ats.engines";
	private static final String ENGINE_ELEMENT = "engine";
	private static final String ID_ATTRIBUTE = "id";
	private static final String CLASS_ATTRIBUTE = "class";
	
    private Map<String, IEngineFactory> engines = new HashMap<String, IEngineFactory>();

	@Override
	public IEngineFactory[] getEngines() {
		Collection<IEngineFactory> c = engines.values();
		return c.toArray(new IEngineFactory[c.size()]);
	}

	@Override
	public IEngineFactory getCompatibleEngineFactory(IStrategy strategy) {
		return engines.get("javascript");
	}

	@Override
	public void startUp() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements = registry.getConfigurationElementsFor(CoreActivator.PLUGIN_ID, EXTENSION_ID);
        for (IConfigurationElement element : elements) {
            try {
                String id = element.getAttribute(ID_ATTRIBUTE);
                if (element.getName().equals(ENGINE_ELEMENT)) {
                    IEngineFactory engine = (IEngineFactory) element.createExecutableExtension(CLASS_ATTRIBUTE);
                    engines.put(id, engine);
                }
            } catch (Exception e) {
                Status status = new Status(IStatus.ERROR, CoreActivator.PLUGIN_ID, 0, "Error creating engine factory " + element.getAttribute(ID_ATTRIBUTE), e); //$NON-NLS-1$
                CoreActivator.getDefault().getLog().log(status);
            }
        }
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}


}
