package fr.epita.iam.services;

import java.util.List;

/*
 * Interface for DAOs
 * */
public interface DAO<T> {
	
	//DAO behaviour
	//public void write(Object identity); ... not a good idea, we can pass not only object but also its address
	public void write(T identity);
	public void update(T identity);
	public void erase(T identity);
	public List<T> search(String criteria);
	public T search(Long uid);
	public List<T> listAll();

}
