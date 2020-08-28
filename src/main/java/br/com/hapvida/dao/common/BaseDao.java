package br.com.hapvida.dao.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao<T> {

	@PersistenceContext
	private EntityManager em;
	
	public EntityManager getEntityManager() {
		return em;
	}
		
	public void inclui(T entidade) {
		em.persist(entidade);
	}
	
	public T atualiza(T entidade) {
		return em.merge(entidade);
	}
	
	public void remove(T entidade) {
		Object o = em.merge(entidade);
		em.remove(o);
	}
	
}
