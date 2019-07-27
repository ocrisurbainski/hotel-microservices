package com.urbainski.reservasapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Cristian Urbainski
 * @since 25/07/2019
 *
 */
@Getter
@AllArgsConstructor
public class ClienteNaoExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Long idCliente;
	
}