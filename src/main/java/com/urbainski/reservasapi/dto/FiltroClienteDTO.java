package com.urbainski.reservasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Cristian Urbainski
 * @since 24/07/2019
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FiltroClienteDTO {

	private String nome;
	
	private String documento;
	
	private String telefone;
	
}