package com.urbainski.reservasapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Cristian Urbainski
 * @since 26/07/2019
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FiltroReservaDTO {

	private Long idCliente;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataInicio;
	
	private LocalDate dataFim;
	
	private CampoPesquisaData campoPesquisaData;
	
	public enum CampoPesquisaData {
		
		DATA_CHECK_IN,
		
		DATA_CHECK_OUT;
	}
	
}