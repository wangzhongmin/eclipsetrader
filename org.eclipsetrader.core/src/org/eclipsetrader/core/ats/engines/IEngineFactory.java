package org.eclipsetrader.core.ats.engines;

import org.eclipsetrader.core.ats.ITradingSystem;
import org.eclipsetrader.core.ats.ITradingSystemContext;

public interface IEngineFactory {

	BaseEngine createEngine(ITradingSystem tradingSystem,
			ITradingSystemContext context);

}
