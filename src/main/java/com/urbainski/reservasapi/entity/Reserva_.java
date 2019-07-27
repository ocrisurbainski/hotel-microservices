package com.urbainski.reservasapi.entity;

import java.time.LocalDateTime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 
 * @author Cristian Urbainski
 * @since 26/07/2019
 *
 */
@StaticMetamodel(Reserva.class)
public abstract class Reserva_ {

	public static volatile SingularAttribute<Reserva, Long> id;
	public static volatile SingularAttribute<Reserva, Cliente> cliente;
	public static volatile SingularAttribute<Reserva, LocalDateTime> dataCheckIn;
	public static volatile SingularAttribute<Reserva, LocalDateTime> dataCheckOut;
	public static volatile SingularAttribute<Reserva, Double> valor;
	public static volatile SingularAttribute<Reserva, Boolean> adicionalVeiculo;
	
}