package com.urbainski.reservasapi.repository.query.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbainski.reservasapi.dto.FiltroClienteDTO;
import com.urbainski.reservasapi.entity.Cliente;
import com.urbainski.reservasapi.repository.query.generic.PageableQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
public interface ClienteRepositoryQuery extends PageableQuery {

	Page<Cliente> findAll(FiltroClienteDTO filtroCliente, Pageable pageable);
	
}