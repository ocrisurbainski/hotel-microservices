package com.urbainski.reservasapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urbainski.reservasapi.entity.Reserva;
import com.urbainski.reservasapi.repository.query.reserva.ReservaRepositoryQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 25/07/2019
 *
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long>, ReservaRepositoryQuery {

	Page<Reserva> findAllByDataCheckOutIsNull(Pageable pageable);
	
	Page<Reserva> findAllByDataCheckOutIsNotNull(Pageable pageable);
	
}