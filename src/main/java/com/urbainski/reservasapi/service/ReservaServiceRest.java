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

import com.urbainski.reservasapi.bo.ReservaBO;
import com.urbainski.reservasapi.dto.FiltroReservaDTO;
import com.urbainski.reservasapi.entity.Reserva;

/**
 * 
 * @author Cristian Urbainski
 * @since 25/07/2019
 *
 */
@RestController
@RequestMapping("/reservas")
public class ReservaServiceRest {

	@Autowired
	private ReservaBO boReserva;

	@PostMapping
	public ResponseEntity<Reserva> save(@Valid @RequestBody Reserva reserva) {
		Reserva reservaSalva = boReserva.save(reserva);
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Reserva> update(@PathVariable Long id, @Valid @RequestBody Reserva reserva) {
		Reserva reservaSalva = boReserva.update(id, reserva);
		return ResponseEntity.ok(reservaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		boReserva.delete(id);
	}

	@GetMapping
	public Page<Reserva> findAll(FiltroReservaDTO filtroReservaDto, Pageable pageable) {
		return boReserva.findAll(filtroReservaDto, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reserva> findOne(@PathVariable Long id) {
		Reserva reserva = boReserva.findOne(id);
		if (reserva == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reserva);
	}
	
	@GetMapping("/check-in")
	public Page<Reserva> findAllByDataCheckOutIsNull(Pageable pageable) {
		return boReserva.findAllByDataCheckOutIsNull(pageable);
	}
	
	@GetMapping("/check-out")
	public Page<Reserva> findAllByDataCheckOutIsNotNull(Pageable pageable) {
		return boReserva.findAllByDataCheckOutIsNotNull(pageable);
	}

}