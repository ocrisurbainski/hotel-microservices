package com.urbainski.reservasapi.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 
 * @author Cristian Urbainski
 * @since 24/07/2019
 *
 */
@StaticMetamodel(Cliente.class)
public abstract class Cliente_ {

	public static volatile SingularAttribute<Cliente, Long> id;
	public static volatile SingularAttribute<Cliente, String> nome;
	public static volatile SingularAttribute<Cliente, String> documento;
	public static volatile SingularAttribute<Cliente, String> telefone;
	
}