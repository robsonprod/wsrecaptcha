package br.com.hapvida.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.hapvida.dao.common.BaseDao;


@Repository
public class RecaptchaDao extends BaseDao<Object> {

	public String pesquisaSecretKey(){
		String hql = "SELECT fn_registro_sistema('RECAPTCHA_SECRET') FROM DUAL";
		Query query = getEntityManager().createNativeQuery(hql);
		return (String) query.getSingleResult();
	}

	public String pesquisarPublicKey() throws NoResultException{
		String hql = "SELECT fn_registro_sistema('RECAPTCHA_PUBLICA') FROM DUAL";
		Query query = getEntityManager().createNativeQuery(hql);
		return (String) query.getSingleResult();
	}

	public String pesquisarRecaptchaFl() throws NoResultException{
		String hql = "SELECT fn_registro_sistema('RECAPTCHA_FL') FROM DUAL";
		Query query = getEntityManager().createNativeQuery(hql);
		return (String) query.getSingleResult();
	}

	public String parametrizacaoValida() {
		String hql = "SELECT fn_registro_sistema('RECAPTCHA_SCORE') FROM DUAL";
		Query query = getEntityManager().createNativeQuery(hql);
		return (String) query.getSingleResult();
	}
	
}
