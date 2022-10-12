package com.hangnv.surgery.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.entity.Session;

@Component
public class SessionSpecification extends BaseSpecification {
	
	public Specification<Session> filter(final SessionDto criteria) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (StringUtils.isNoneBlank(criteria.getCode())) {
				String code = queryFilter(criteria.getCode());
				predicates.add(cb.like(root.get("code"), code));
			}
			
			if (StringUtils.isNoneBlank(criteria.getDiagnosis())) {
				String diagnosis = queryFilter(criteria.getDiagnosis());
				predicates.add(cb.like(cb.upper(root.get("diagnosis")), diagnosis));
			}
			
			if (StringUtils.isNoneBlank(criteria.getSymptom())) {
				String symptom = queryFilter(criteria.getSymptom());
				predicates.add(cb.like(cb.upper(root.get("symptom")), symptom));
			}
			
			if (criteria.getSearchFromDate() != null && criteria.getSearchToDate() != null) {
				predicates.add(cb.between(root.get("createdDate"), criteria.getSearchFromDate(), criteria.getSearchToDate()));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}

}
