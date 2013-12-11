package org.eclipsetrader.archipelago;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class ArchipelagoPlugin implements BundleActivator {

    public static final String PLUGIN_ID = "org.eclipsetrader.archipelago";

    private static ArchipelagoPlugin plugin;

	private BundleContext m_context;

    /**
     * The constructor.
     */
    public ArchipelagoPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
    	m_context = context;
    }

    /**
     * This method is called when the plug-in is stopped
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static ArchipelagoPlugin getDefault() {
        return plugin;
    }

    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }

	public final ILog getLog() {
		return InternalPlatform.getDefault().getLog(getBundle());
	}

	 public Bundle getBundle(){
	    	return m_context.getBundle();
	    }
}
