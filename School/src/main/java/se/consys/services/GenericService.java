package se.consys.services;

import java.io.Serializable;
import java.util.List;

import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.dataaccess.IGenericDao;

public class GenericService<T extends Serializable> implements IGenericDao<T> {

	private IGenericDao dao;
	
	private GenericService(IGenericDao dao) {
		this.dao = dao;
	}
	
	public static GenericService getGenericService(IGenericDao dao) {
		return new GenericService(dao);		
	}
	
	@Override
	public void create(T entity) {
		dao.create(entity);
	}

	@Override
	public T update(T entity) {
		return (T) dao.update(entity);		
	}

	@Override
	public void delete(T entity) {
		dao.delete(entity);
	}

	@Override
	public T findById(int id) {
		return (T) dao.findById(id);
	}

	@Override
	public List<T> findAll() {
		 return dao.findAll();
	}
	
	@Override
	public boolean Login(String email, String password) {
		return dao.Login(email, password);
	}

	public IGenericDao getDao() {
		return dao;
	}

	public void setDao(IGenericDao dao) {
		this.dao = dao;
	}

	@Override
	public void removeReference(T entity, Class<?> reference) {
		this.dao.removeReference(entity, reference);
	}



}
