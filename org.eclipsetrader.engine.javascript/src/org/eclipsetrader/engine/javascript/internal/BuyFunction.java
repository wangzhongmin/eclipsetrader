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

package org.eclipsetrader.engine.javascript.internal;

import org.eclipsetrader.core.instruments.ISecurity;
import org.eclipsetrader.core.trading.IAccount;
import org.eclipsetrader.core.trading.IBroker;
import org.eclipsetrader.core.trading.IOrderSide;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

public class BuyFunction extends BaseOrderFunction {

    private static final long serialVersionUID = 6032038613561903364L;

    public BuyFunction() {
    }

    public BuyFunction(IBroker broker, IAccount account, ISecurity security) {
        super(broker, account, security);
    }

    public static Object jsConstructor(Context cx, Object[] args, Function ctorObj, boolean inNewExpr) {
        if (inNewExpr) {
            IBroker broker = (IBroker) ScriptableObject.getProperty(getTopLevelScope(ctorObj), PROPERTY_BROKER);
            IAccount account = (IAccount) ScriptableObject.getProperty(getTopLevelScope(ctorObj), PROPERTY_ACCOUNT);
            ISecurity instrument = (ISecurity) ScriptableObject.getProperty(getTopLevelScope(ctorObj), PROPERTY_INSTRUMENT);

            BuyFunction result = new BuyFunction(broker, account, instrument);
            result.side = IOrderSide.Buy;

            int index = 0;
            if (args.length >= index + 1) {
                result.jsSet_quantity(args[index]);
                index++;
            }
            if (args.length >= index + 1) {
                result.jsSet_price(args[index]);
                index++;
            }
            if (args.length >= index + 1) {
                result.jsSet_text(args[index]);
                index++;
            }

            return result;
        }
        else {
            return BaseOrderFunction.Buy;
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName() {
        return "Buy";
    }

    /* (non-Javadoc)
     * @see org.eclipsetrader.core.ats.engines.AbstractOrderFunction#jsFunction_send()
     */
    @Override
    public Object jsFunction_send() throws Exception {
        return super.jsFunction_send();
    }

    /* (non-Javadoc)
     * @see org.eclipsetrader.core.ats.engines.BaseOrderFunction#jsFunction_cancel()
     */
    @Override
    public Object jsFunction_cancel() throws Exception {
        return super.jsFunction_cancel();
    }
}
