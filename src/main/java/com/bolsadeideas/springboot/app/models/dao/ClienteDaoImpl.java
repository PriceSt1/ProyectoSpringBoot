package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked") //Para que no salten los errores que no son importantes
	@Transactional(readOnly=true) //Para indicar que es solo lectura, se pone al hacer select
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		if (cliente.getId()!= null && cliente.getId()>0) {
			em.merge(cliente);
		}else {			
			em.persist(cliente);
		}
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
		
	}

	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class,id);
	}


	
}
