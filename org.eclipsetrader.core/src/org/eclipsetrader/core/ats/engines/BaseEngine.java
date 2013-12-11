package org.eclipsetrader.core.ats.engines;

import java.util.Observable;
import java.util.Observer;

import org.eclipsetrader.core.instruments.ISecurity;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public abstract class BaseEngine extends Observable{

	public abstract void start() throws Exception;

	public abstract void stop();

	public abstract void dispose();

	public abstract Object get(String name);

	public abstract IEngineInstrument getContextFor(ISecurity security);

}