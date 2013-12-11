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

package org.eclipsetrader.core.internal.ats;

import org.eclipsetrader.core.ats.ITradingSystemProperties;
import org.eclipsetrader.core.trading.IAccount;
import org.eclipsetrader.core.trading.IBroker;

public class TradingSystemProperties implements ITradingSystemProperties {

    private boolean autostart;
    private IBroker broker;
    private IAccount account;
    private int backfill;

    public TradingSystemProperties() {
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#isAutostart()
	 */
    @Override
	public boolean isAutostart() {
        return autostart;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#setAutostart(boolean)
	 */
    @Override
	public void setAutostart(boolean autostart) {
        this.autostart = autostart;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#getBroker()
	 */
    @Override
	public IBroker getBroker() {
        return broker;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#setBroker(org.eclipsetrader.core.trading.IBroker)
	 */
    @Override
	public void setBroker(IBroker broker) {
        this.broker = broker;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#getAccount()
	 */
    @Override
	public IAccount getAccount() {
        return account;
    }

    /* (non-Javadoc)
	 * @see org.eclipsetrader.core.internal.ats.ITradingSystemProperties#setAccount(org.eclipsetrader.core.trading.IAccount)
	 */
    @Override
	public void setAccount(IAccount account) {
        this.account = account;
    }

    public int getBackfill() {
        return backfill;
    }

    public void setBackfill(int backfill) {
        this.backfill = backfill;
    }
}
