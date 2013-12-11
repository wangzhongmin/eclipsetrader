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

package org.eclipsetrader.internal.brokers.paper;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator  {

    public static final String PLUGIN_ID = "org.eclipsetrader.brokers.paper";

    private static Activator plugin;

    private AccountRepository repository;

	private BundleContext m_context;

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
    	m_context = context;
        plugin = this;

        repository = new AccountRepository();
        repository.load(getStateLocation().append("accounts.xml").toFile());
    }

    public final IPath getStateLocation() throws IllegalStateException {
		return InternalPlatform.getDefault().getStateLocation(getBundle(), true);
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
        repository.save(getStateLocation().append("accounts.xml").toFile());

        new PaperBrokerFactory().dispose();

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

    public static void log(IStatus status) {
        if (plugin == null) {
            throw new RuntimeException(status.getMessage(), status.getException());
        }
        plugin.getLog().log(status);
    }
    
	public final ILog getLog() {
		return InternalPlatform.getDefault().getLog(getBundle());
	}

    public AccountRepository getRepository() {
        return repository;
    }
}
