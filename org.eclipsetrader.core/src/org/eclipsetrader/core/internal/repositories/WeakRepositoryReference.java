package org.eclipsetrader.core.internal.repositories;

import org.eclipsetrader.core.repositories.IRepository;
import org.eclipsetrader.core.repositories.IRepositoryReference;
import org.eclipsetrader.core.repositories.IStoreObject;

public class WeakRepositoryReference implements IRepositoryReference {

	@Override
	public IRepository get(IStoreObject object) {
		return object.getStore().getRepository();
	}

}
