package com.urbainski.reservasapi.repository.query.reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbainski.reservasapi.dto.FiltroReservaDTO;
import com.urbainski.reservasapi.entity.Reserva;
import com.urbainski.reservasapi.repository.query.generic.PageableQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 26/07/2019
 *
 */
public interface ReservaRepositoryQuery extends PageableQuery {

	Page<Reserva> findAll(FiltroReservaDTO filtroReservaDto, Pageable pageable);
	
}