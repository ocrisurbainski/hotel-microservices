package com.urbainski.reservasapi.repository.query.reserva.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbainski.reservasapi.dto.FiltroReservaDTO;
import com.urbainski.reservasapi.entity.Reserva;
import com.urbainski.reservasapi.entity.Reserva_;
import com.urbainski.reservasapi.repository.query.reserva.ReservaRepositoryQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 26/07/2019
 *
 */
public class ReservaRepositoryQueryImpl implements ReservaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Page<Reserva> findAll(FiltroReservaDTO filtroReservaDto, Pageable pageable) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
		Root<Reserva> from = cq.from(Reserva.class);
		
		List<Predicate> where = new ArrayList<Predicate>();
		
		if (filtroReservaDto.getIdCliente() != null) {
			where.add(cb.equal(from.get(Reserva_.cliente), filtroReservaDto.getIdCliente()));
		}
		
		if (filtroReservaDto.getCampoPesquisaData() != null) {
			
			Path<LocalDateTime> pathCampoData = null;
			if (FiltroReservaDTO.CampoPesquisaData.DATA_CHECK_IN.equals(filtroReservaDto.getCampoPesquisaData())) {
				pathCampoData = from.get(Reserva_.dataCheckIn);
			} else {
				pathCampoData = from.get(Reserva_.dataCheckOut);
			}
			
			Predicate predicate = null;
			
			if (filtroReservaDto.getDataInicio() != null && filtroReservaDto.getDataFim() != null) {
				LocalDateTime dataInicio = LocalDateTime.of(filtroReservaDto.getDataInicio(), LocalTime.of(0, 0, 0));
				LocalDateTime dataFim = LocalDateTime.of(filtroReservaDto.getDataFim(), LocalTime.of(23, 59, 59));
				predicate = cb.between(pathCampoData, dataInicio, dataFim);
			} else if (filtroReservaDto.getDataInicio() != null) {
				LocalDateTime dataInicio = LocalDateTime.of(filtroReservaDto.getDataInicio(), LocalTime.of(0, 0, 0));
				predicate = cb.greaterThanOrEqualTo(pathCampoData, dataInicio);
			} else if (filtroReservaDto.getDataFim() != null) {
				LocalDateTime dataFim = LocalDateTime.of(filtroReservaDto.getDataFim(), LocalTime.of(23, 59, 59));
				predicate = cb.lessThanOrEqualTo(pathCampoData, dataFim);
			}
			
			where.add(predicate);
		}
		
		cq.where(where.toArray(new Predicate[] {}));
		
		return applyPageable(entityManager, cq, from, pageable);
	}
	
}