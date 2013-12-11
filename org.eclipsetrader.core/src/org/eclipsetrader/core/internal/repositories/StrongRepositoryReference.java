package org.eclipsetrader.core.internal.repositories;

import org.eclipsetrader.core.repositories.IRepository;
import org.eclipsetrader.core.repositories.IRepositoryReference;
import org.eclipsetrader.core.repositories.IStoreObject;

public class StrongRepositoryReference implements IRepositoryReference {
	
	private IRepository m_repository;

	public StrongRepositoryReference(IRepository defaultRepository) {
		m_repository = defaultRepository;
	}

	@Override
	public IRepository get(IStoreObject object) {
		return m_repository;
	}

}
