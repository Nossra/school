package se.consys.dataaccess;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Session;

import se.consys.Utilities.Helper;
import se.consys.Utilities.HibernateUtility;
import se.consys.services.GenericService;

public class DaoGenericHibernateImpl<T extends Serializable> implements IGenericDao<T> {

	Session session = HibernateUtility.getSessionFactory().openSession();
	private String activeClassName;
	private String wrongClassError = "ERROR: Wrong class used on the established service.";
	
	public DaoGenericHibernateImpl(Class<T> type) {
		activeClassName = type.getSimpleName();
	}
	
	@Override
	public void create(T entity) {
		if (entity.getClass().getSimpleName().equals(activeClassName)) {
			session.beginTransaction();
			session.persist(entity);
			session.getTransaction().commit();
		} else {
			System.out.println(wrongClassError + " Entity has not been saved to the database. "
				+ "Class used: " + entity.getClass().getSimpleName() + ". "
				+ "Class expected: " + activeClassName + ".");
		}
	}

	@Override
	public T update(T entity) {
		if (entity.getClass().getSimpleName().equals(activeClassName)) {
			session.beginTransaction();
			session.merge(entity); 
			session.getTransaction().commit();
			return entity;
		} else {
			System.out.println(wrongClassError + " Entity has not been updated. "
				+ "Class used: " + entity.getClass().getSimpleName() + ". "
				+ "Class expected: " + activeClassName + ".");
		}
		return entity;
	}

	@Override
	public void delete(T entity) {
		if (entity.getClass().getSimpleName().equals(activeClassName)) {
			session.beginTransaction();
			session.delete(entity);

			session.getTransaction().commit();
		} else {
			System.out.println(wrongClassError + " Entity has not been deleted. "
				+ "Class used: " + entity.getClass().getSimpleName() + ". "
				+ "Class expected: " + activeClassName + ".");
		}		
	}

	@Override
	public T findById(int id) {
		final String HQL_BY_ID = "FROM " + activeClassName + " WHERE id=:id";
		
		@SuppressWarnings("unchecked")
		T result = (T) session.createQuery(HQL_BY_ID)
			.setParameter("id", id)
			.setMaxResults(1)
			.getSingleResult();		
		return  result;
	}

	@Override
	public List<T> findAll() {
		String HQL_FIND_ALL = "FROM " + activeClassName;
		
		@SuppressWarnings("unchecked")
		List<T> result = (List<T>) session.createQuery(HQL_FIND_ALL)
			.getResultList();
		return  result;
	}

	@Override
	public void removeReference(T entity, Class<?> reference) {
		Method setter = Helper.findSetter(entity, reference);
		try {
			setter.invoke(entity, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
