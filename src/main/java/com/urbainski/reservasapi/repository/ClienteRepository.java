package com.urbainski.reservasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urbainski.reservasapi.entity.Cliente;
import com.urbainski.reservasapi.repository.query.cliente.ClienteRepositoryQuery;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery {

}