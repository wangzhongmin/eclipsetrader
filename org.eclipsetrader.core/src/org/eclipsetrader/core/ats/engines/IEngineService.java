package org.eclipsetrader.core.ats.engines;

import org.eclipsetrader.core.ats.IStrategy;

public interface IEngineService {
	
	public abstract IEngineFactory[] getEngines();

	public abstract IEngineFactory getCompatibleEngineFactory(IStrategy strategy);
	
	void startUp();
	
	void shutDown();

}
