package org.eclipsetrader.core.ats;

import org.eclipsetrader.core.trading.IAccount;
import org.eclipsetrader.core.trading.IBroker;

public interface ITradingSystemProperties {

	public abstract boolean isAutostart();

	public abstract void setAutostart(boolean autostart);

	public abstract IBroker getBroker();

	public abstract void setBroker(IBroker broker);

	public abstract IAccount getAccount();

	public abstract void setAccount(IAccount account);

}