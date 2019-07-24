package com.urbainski.reservasapi.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.urbainski.reservasapi.bo.ClienteBO;
import com.urbainski.reservasapi.dto.FiltroClienteDTO;
import com.urbainski.reservasapi.entity.Cliente;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
@RestController
@RequestMapping("/clientes")
public class ClienteServiceRest {
	
	@Autowired
	private ClienteBO boCliente;

	@PostMapping
	public ResponseEntity<Cliente> save(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = boCliente.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = boCliente.update(id, cliente);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		boCliente.delete(id);
	}
	
	@GetMapping
	public Page<Cliente> findAll(FiltroClienteDTO filtroCliente, Pageable pageable) {
		return boCliente.findAll(filtroCliente, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
		Cliente cliente = boCliente.findOne(id);
		if (cliente == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
}