package com.urbainski.reservasapi.repository.query.cliente.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.urbainski.reservasapi.dto.FiltroClienteDTO;
import com.urbainski.reservasapi.entity.Cliente;
import com.urbainski.reservasapi.entity.Cliente_;
import com.urbainski.reservasapi.repository.query.cliente.ClienteRepositoryQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
public class ClienteRepositoryQueryImpl implements ClienteRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Cliente> findAll(FiltroClienteDTO filtroCliente, Pageable pageable) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
		Root<Cliente> from = cq.from(Cliente.class);
		
		List<Predicate> where = new ArrayList<Predicate>();
		
		if (!StringUtils.isEmpty(filtroCliente.getNome())) {
			Expression<String> lowerCaseFieldNome = cb.function("lower", String.class, from.get(Cliente_.nome));
			where.add(cb.like(lowerCaseFieldNome, "%" + filtroCliente.getNome().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(filtroCliente.getDocumento())) {
			Expression<String> lowerCaseFieldDocumento = cb.function("lower", String.class, from.get(Cliente_.documento));
			where.add(cb.like(lowerCaseFieldDocumento, "%" + filtroCliente.getDocumento().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(filtroCliente.getTelefone())) {
			Expression<String> lowerCaseFieldTelefone = cb.function("lower", String.class, from.get(Cliente_.telefone));
			where.add(cb.like(lowerCaseFieldTelefone, "%" + filtroCliente.getTelefone().toLowerCase() + "%"));
		}
		
		cq.where(where.toArray(new Predicate[] {}));
		
		return applyPageable(entityManager, cq, from, pageable);
	}
	
}