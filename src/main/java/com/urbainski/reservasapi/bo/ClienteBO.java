package com.urbainski.reservasapi.bo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urbainski.reservasapi.dto.FiltroClienteDTO;
import com.urbainski.reservasapi.entity.Cliente;
import com.urbainski.reservasapi.entity.Cliente_;
import com.urbainski.reservasapi.exceptions.ClienteNaoExisteException;
import com.urbainski.reservasapi.repository.ClienteRepository;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
@Service
public class ClienteBO {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Cliente update(Long id, Cliente cliente) {
		Cliente clienteDatabase = findById(id);
		
		/**
		 * Copia as propriedades de um objeto para o outro ignorando o id.
		 */
		BeanUtils.copyProperties(cliente, clienteDatabase, Cliente_.id.getName());
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Page<Cliente> findAll(FiltroClienteDTO filtroCliente, Pageable pageable) {
		return clienteRepository.findAll(filtroCliente, pageable);
	}
	
	public Cliente findById(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		if (cliente == null) {
			throw new ClienteNaoExisteException(id);
		}
		return cliente;
	}
	
	public Cliente findOne(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

}