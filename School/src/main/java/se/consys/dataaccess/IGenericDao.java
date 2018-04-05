package se.consys.dataaccess;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable>  {
	void create(T entity);
	T update(T entity);
	void delete(T entity);
	T findById(int id);
	List<T> findAll();
}
