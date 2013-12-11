package org.eclipsetrader.core.ats.engines;

import org.eclipsetrader.core.feed.IBar;
import org.eclipsetrader.core.feed.IBarOpen;
import org.eclipsetrader.core.feed.IQuote;
import org.eclipsetrader.core.feed.ITrade;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.trading.IPosition;
import org.mozilla.javascript.Scriptable;

public interface IEngineInstrument {

	public abstract ISecurity getInstrument();

	public abstract void onStrategyStart();

	public abstract void onQuote(IQuote quote);

	public abstract void onTrade(ITrade trade);

	public abstract void onBarOpen(IBarOpen bar);

	public abstract void onBar(IBar bar);

	public abstract void onPositionOpen(IPosition position);

	public abstract void onPositionChange(IPosition position);

	public abstract void onPositionClosed(IPosition position);

	public abstract void setPosition(IPosition position);

	public abstract Object get(String name);

	public abstract IBar[] getBars();

	public abstract void backfill(int backfillBars);

}