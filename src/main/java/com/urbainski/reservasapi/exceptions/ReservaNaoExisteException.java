package com.urbainski.reservasapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Cristian Urbainski
 * @since 27/07/2019
 *
 */
@Getter
@AllArgsConstructor
public class ReservaNaoExisteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private Long idReserva;
	
}