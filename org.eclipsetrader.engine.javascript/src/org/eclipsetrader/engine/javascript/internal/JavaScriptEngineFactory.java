package org.eclipsetrader.engine.javascript.internal;

import org.eclipsetrader.core.ats.ITradingSystem;
import org.eclipsetrader.core.ats.ITradingSystemContext;
import org.eclipsetrader.core.ats.engines.BaseEngine;
import org.eclipsetrader.core.ats.engines.IEngineFactory;

public class JavaScriptEngineFactory implements IEngineFactory {

	@Override
	public BaseEngine createEngine(ITradingSystem tradingSystem,
			ITradingSystemContext context) {
		return new JavaScriptEngine(tradingSystem, context);
	}

}
