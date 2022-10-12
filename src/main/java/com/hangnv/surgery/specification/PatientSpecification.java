package com.hangnv.surgery.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.entity.Patient;

@Component
public class PatientSpecification extends BaseSpecification {

	public Specification<Patient> filter(final PatientDto criteria) {
		return (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				
				if (StringUtils.isNoneBlank(criteria.getName())) {
					String name = queryFilter(criteria.getName());
					predicates.add(cb.like(cb.upper(root.get("name")), name));
				}
				
				return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
	
}
