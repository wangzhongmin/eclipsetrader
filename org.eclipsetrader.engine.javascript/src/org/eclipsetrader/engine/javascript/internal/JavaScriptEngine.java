/*
 * Copyright (c) 2004-2012 Marco Maccaferri and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marco Maccaferri - initial API and implementation
 */

package org.eclipsetrader.engine.javascript.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.eclipsetrader.core.ats.IScriptStrategy;
import org.eclipsetrader.core.ats.ITradingSystem;
import org.eclipsetrader.core.ats.ITradingSystemContext;
import org.eclipsetrader.core.ats.ITradingSystemInstrument;
import org.eclipsetrader.core.ats.engines.BaseEngine;
import org.eclipsetrader.core.ats.engines.EngineEvent;
import org.eclipsetrader.core.ats.engines.IEngineInstrument;
import org.eclipsetrader.core.charts.IDataSeries;
import org.eclipsetrader.core.feed.IBar;
import org.eclipsetrader.core.feed.IBarOpen;
import org.eclipsetrader.core.feed.IPricingEnvironment;
import org.eclipsetrader.core.feed.IPricingListener;
import org.eclipsetrader.core.feed.IQuote;
import org.eclipsetrader.core.feed.ITrade;
import org.eclipsetrader.core.feed.PricingDelta;
import org.eclipsetrader.core.feed.PricingEvent;
import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.trading.IAccount;
import org.eclipsetrader.core.trading.IBroker;
import org.eclipsetrader.core.trading.IPosition;
import org.eclipsetrader.core.trading.IPositionListener;
import org.eclipsetrader.core.trading.PositionEvent;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptEngine extends BaseEngine {

    public static final String PROPERTY_INSTRUMENTS = "instruments"; //$NON-NLS-1$
    public static final String PROPERTY_POSITIONS = "positions"; //$NON-NLS-1$
    public static final String PROPERTY_MARKET = "market"; //$NON-NLS-1$
    public static final String PROPERTY_BARSIZE = "barSize"; //$NON-NLS-1$

    private final ITradingSystem tradingSystem;
    private final ITradingSystemContext context;
    private final IScriptStrategy strategy;
    private final IPricingEnvironment pricingEnvironment;
    private final IAccount account;
    private final IBroker broker;

    private ScriptableObject scope;
    private Map<String, Object> instrumentsMap = new HashMap<String, Object>();
    private Map<Object, IPosition> positionsMap = new HashMap<Object, IPosition>();
    private Map<ISecurity, JavaScriptEngineInstrument> contextsMap = new HashMap<ISecurity, JavaScriptEngineInstrument>();

    public IPricingListener pricingListener = new IPricingListener() {

        @Override
        public void pricingUpdate(PricingEvent event) {
            doPricingUpdate(event);
        }
    };

    private IPositionListener positionListener = new IPositionListener() {

        @Override
        public void positionOpened(PositionEvent e) {
            IEngineInstrument context = contextsMap.get(e.position.getSecurity());
            if (context == null) {
                return;
            }
            context.onPositionOpen(e.position);
            updatePositionsMap();
            setChanged();
            notifyObservers(new EngineEvent(e.position.getSecurity(), e.position));
        }

        @Override
        public void positionClosed(PositionEvent e) {
            IEngineInstrument context = contextsMap.get(e.position.getSecurity());
            if (context == null) {
                return;
            }
            context.onPositionClosed(e.position);
            updatePositionsMap();
            setChanged();
            notifyObservers(new EngineEvent(e.position.getSecurity(), e.position));
        }

        @Override
        public void positionChanged(PositionEvent e) {
            IEngineInstrument context = contextsMap.get(e.position.getSecurity());
            if (context == null) {
                return;
            }
            context.onPositionChange(e.position);
            updatePositionsMap();
            setChanged();
            notifyObservers(new EngineEvent(e.position.getSecurity(), e.position));
        }
    };

    public JavaScriptEngine(ITradingSystem tradingSystem, ITradingSystemContext context) {
        this.tradingSystem = tradingSystem;
        this.context = context;
        this.strategy = (IScriptStrategy) tradingSystem.getStrategy();
        this.pricingEnvironment = context.getPricingEnvironment();
        this.account = context.getAccount();
        this.broker = context.getBroker();
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#start()
	 */
    @Override
	public void start() throws Exception {
        Context cx = Context.enter();
        try {
            cx.setWrapFactory(new EnhancedWrapFactory());

            scope = new ImporterTopLevel(cx);

            scope.putConst("Buy", scope, BaseOrderFunction.Buy);
            scope.putConst("Sell", scope, BaseOrderFunction.Sell);
            scope.putConst("Limit", scope, BaseOrderFunction.Limit);
            scope.putConst("Market", scope, BaseOrderFunction.Market);
            scope.putConst("Above", scope, IDataSeries.ABOVE);
            scope.putConst("Below", scope, IDataSeries.BELOW);
            scope.putConst("None", scope, IDataSeries.NONE);

            ScriptableObject.putProperty(scope, BaseOrderFunction.PROPERTY_ACCOUNT, Context.javaToJS(account, scope));
            ScriptableObject.putProperty(scope, BaseOrderFunction.PROPERTY_BROKER, Context.javaToJS(broker, scope));
            ScriptableObject.putProperty(scope, PROPERTY_INSTRUMENTS, instrumentsMap);
            ScriptableObject.putProperty(scope, PROPERTY_POSITIONS, positionsMap);
            ScriptableObject.putProperty(scope, PROPERTY_BARSIZE, tradingSystem.getStrategy().getBarsTimeSpan());

            ScriptableObject.putProperty(scope, "out", Context.javaToJS(System.out, scope));

            updatePositionsMap();

            int backfillSize = context.getInitialBackfillSize();

            for (ITradingSystemInstrument instrument : tradingSystem.getInstruments()) {
                ISecurity security = instrument.getInstrument();
                JavaScriptEngineInstrument engineInstrument = new JavaScriptEngineInstrument(scope, security, strategy);
                if (backfillSize != 0) {
                    engineInstrument.backfill(backfillSize);
                }
                if (security.getIdentifier() != null) {
                    Object jsInstrument = engineInstrument.getScope().get(BaseOrderFunction.PROPERTY_INSTRUMENT, engineInstrument.getScope());
                    instrumentsMap.put(security.getIdentifier().getSymbol(), jsInstrument);
                }
                engineInstrument.setPosition(positionsMap.get(security));
                contextsMap.put(instrument.getInstrument(), engineInstrument);
            }

            for (IEngineInstrument engineInstrument : contextsMap.values()) {
                engineInstrument.onStrategyStart();
            }

            account.addPositionListener(positionListener);
            pricingEnvironment.addPricingListener(pricingListener);
        } finally {
            Context.exit();
        }
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#stop()
	 */
    @Override
	public void stop() {
        contextsMap.clear();

        instrumentsMap.clear();
        positionsMap.clear();

        account.removePositionListener(positionListener);
        pricingEnvironment.removePricingListener(pricingListener);

        scope = null;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#dispose()
	 */
    @Override
	public void dispose() {
        deleteObservers();
    }

    void doPricingUpdate(PricingEvent event) {
        IEngineInstrument instrument = contextsMap.get(event.getSecurity());
        if (instrument == null) {
            return;
        }
        for (PricingDelta delta : event.getDelta()) {
            Object value = delta.getNewValue();
            if (value instanceof IQuote) {
                instrument.onQuote((IQuote) value);
                setChanged();
                notifyObservers(new EngineEvent(event.getSecurity(), value));
            }
            else if (value instanceof ITrade) {
                instrument.onTrade((ITrade) value);
                setChanged();
                notifyObservers(new EngineEvent(event.getSecurity(), value));
            }
            else if (value instanceof IBarOpen) {
                instrument.onBarOpen((IBarOpen) value);
                setChanged();
                notifyObservers(new EngineEvent(event.getSecurity(), value));
            }
            else if (value instanceof IBar) {
                instrument.onBar((IBar) value);
                setChanged();
                notifyObservers(new EngineEvent(event.getSecurity(), instrument.getBars()));
            }
        }
    }

    void updatePositionsMap() {
        positionsMap.clear();
        for (IPosition position : account.getPositions()) {
            ISecurity security = position.getSecurity();
            if (security.getIdentifier() != null) {
                positionsMap.put(security.getIdentifier().getSymbol(), position);
                positionsMap.put(security, position);
            }
        }
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#get(java.lang.String)
	 */
    @Override
	public Object get(String name) {
        return scope.get(name, scope);
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#getScope()
	 */
    public ScriptableObject getScope() {
        return scope;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.ats.engines.IEngine9#getContextFor(org.eclipsetrader.core.instruments.ISecurity)
	 */
    @Override
	public IEngineInstrument getContextFor(ISecurity security) {
        return contextsMap.get(security);
    }
}
