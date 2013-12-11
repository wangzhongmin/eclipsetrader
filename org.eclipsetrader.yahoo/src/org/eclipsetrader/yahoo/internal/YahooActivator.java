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

package org.eclipsetrader.yahoo.internal;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipsetrader.core.internal.CoreActivator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class YahooActivator implements BundleActivator {
	BundleContext m_context;

    // The shared instance
    private static YahooActivator plugin;
	
    // The plug-in ID
    public static final String PLUGIN_ID = "org.eclipsetrader.yahoo";
    public static final String REPOSITORY_FILE = "identifiers.xml"; //$NON-NLS-1$

    public static final String PREFS_DRIVER = "DRIVER"; //$NON-NLS-1$
    public static final String PREFS_NEWS_UPDATE_INTERVAL = "NEWS_UPDATE_INTERVAL"; //$NON-NLS-1$
    public static final String PREFS_HOURS_AS_RECENT = "HOURS_AS_RECENT"; //$NON-NLS-1$
    public static final String PREFS_UPDATE_SECURITIES_NEWS = "UPDATE_SECURITIES_NEWS"; //$NON-NLS-1$
    public static final String PREFS_SUBSCRIBE_PREFIX = "SUBSCRIBE_"; //$NON-NLS-1$

	@Override
	public void start(BundleContext context) throws Exception {
		m_context = context;
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public final BundleContext getBundleContext() {
		return m_context;
	}

	public final static YahooActivator getDefault() {
		return plugin;
	}
	
    public void log(IStatus status) {
        getLog().log(status);
    }

	/**
	 * Returns the log for this plug-in.  If no such log exists, one is created.
	 *
	 * @return the log for this plug-in
	 * XXX change this into a LogMgr service that would keep track of the map. See if it can be a service factory.
	 */
	public final ILog getLog() {
		return InternalPlatform.getDefault().getLog(m_context.getBundle());
	}

}
