package com.urbainski.reservasapi.repository.query.generic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author Cristian Urbainski
 * @since 23/07/2019
 *
 */
public interface PageableQuery {

	default <T> Page<T> applyPageable(EntityManager entityManager, CriteriaQuery<T> query, Root<T> rootQuery, Pageable pageable) {
		int actualPage = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = actualPage * pageSize;
		
		TypedQuery<T> typedQuery = entityManager.createQuery(query);
		typedQuery.setMaxResults(pageSize);
		typedQuery.setFirstResult(offset);
		
		List<T> list = typedQuery.getResultList();
		
		long totalRecords = countTotalRecords(entityManager, query, rootQuery);
		
		return new PageImpl<T>(list, pageable, totalRecords);
	}
	
	default <T> long countTotalRecords(EntityManager entityManager, CriteriaQuery<T> query, Root<T> rootQuery) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		Root<? extends T> fromCount = cqCount.from(rootQuery.getJavaType());
		
		cqCount.select(cb.count(fromCount));
		
		if (query.getRestriction() != null) {
			cqCount.where(query.getRestriction());
		}
		
		return entityManager.createQuery(cqCount).getSingleResult();
	}
	
}