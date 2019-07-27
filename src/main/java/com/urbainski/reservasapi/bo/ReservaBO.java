package com.urbainski.reservasapi.bo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urbainski.reservasapi.dto.FiltroReservaDTO;
import com.urbainski.reservasapi.entity.Cliente;
import com.urbainski.reservasapi.entity.Reserva;
import com.urbainski.reservasapi.exceptions.ClienteNaoExisteException;
import com.urbainski.reservasapi.repository.ClienteRepository;
import com.urbainski.reservasapi.repository.ReservaRepository;

/**
 * 
 * @author Cristian Urbainski
 * @since 25/07/2019
 *
 */
@Service
public class ReservaBO {

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CalcularValorReservaBO boCalcularValorReserva;
	
	@Transactional(propagation =  Propagation.REQUIRED)
	public Reserva save(Reserva reserva) {
		
		validarCliente(reserva.getCliente());
		
		Double valorReserva = boCalcularValorReserva.calcularValor(reserva);
		reserva.setValor(valorReserva);
		
		return reservaRepository.save(reserva);
	}

	@Transactional(propagation =  Propagation.REQUIRED)
	public Reserva update(Long id, Reserva reserva) {
		
		validarCliente(reserva.getCliente());
		
		Reserva reservaDatabase = findById(id);
		
		/**
		 * Copia as propriedades de um objeto para o outro ignorando o id.
		 */
		BeanUtils.copyProperties(reserva, reservaDatabase, "id", "valor");
		
		Double valorReserva = boCalcularValorReserva.calcularValor(reserva);
		reservaDatabase.setValor(valorReserva);
		
		return reservaRepository.save(reservaDatabase);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		reservaRepository.deleteById(id);
	}
	
	public Page<Reserva> findAll(FiltroReservaDTO filtroReservaDto, Pageable pageable) {
		return reservaRepository.findAll(filtroReservaDto, pageable);
	}
	
	public Reserva findById(Long id) {
		Reserva reserva = reservaRepository.findById(id).orElse(null);
		if (reserva == null) {
			throw new EmptyResultDataAccessException("Não foi localizado a reserva com o identificador [" + id + "].", 1);
		}
		return reserva;
	}

	public Reserva findOne(Long id) {
		return reservaRepository.findById(id).orElse(null);
	}
	
	/**
	 * Método responsável pelas valições do cliente para poder salvar as informações da reserva.
	 * 
	 * @param cliente - cliente a ser validado
	 */
	private void validarCliente(Cliente cliente) {

		boolean clienteExiste = cliente.getId() != null;
		
		if (clienteExiste) {
			clienteExiste = clienteRepository.existsById(cliente.getId());
		}
		
		if (!clienteExiste) {
			throw new ClienteNaoExisteException();
		}
	}

}