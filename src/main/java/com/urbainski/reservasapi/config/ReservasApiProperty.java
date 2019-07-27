package com.urbainski.reservasapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Cristian Urbainski
 * @since 26/07/2019
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties("reservas")
public class ReservasApiProperty {

	private String originAllowed;
	
}