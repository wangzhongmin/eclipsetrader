/*
 * Copyright (c) 2004-2011 Marco Maccaferri and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marco Maccaferri - initial API and implementation
 */

package org.eclipsetrader.repository.local.internal;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipsetrader.repository.local.LocalRepository;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.eclipsetrader.repository.local";

    // The shared instance
    private static Activator plugin;
    
    private BundleContext m_context;

    private LocalRepository repository;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        plugin = this;

        m_context = context;
        repository = new LocalRepository(getStateLocation());
        repository.startUp();
    }

    public Bundle getBundle(){
    	return m_context.getBundle();
    }
    
    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        repository.shutDown();

        plugin = null;
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    public LocalRepository getRepository() {
        return repository;
    }

    public static void log(IStatus status) {
        if (plugin != null) {
            plugin.getLog().log(status);
        }
        else if (status.getException() != null) {
            throw new RuntimeException(status.getException());
        }
        else {
            System.err.println(status);
        }
    }
    
	public final IPath getStateLocation() throws IllegalStateException {
		return InternalPlatform.getDefault().getStateLocation(getBundle(), true);
	}
	
	public final ILog getLog() {
		return InternalPlatform.getDefault().getLog(getBundle());
	}
    
}
