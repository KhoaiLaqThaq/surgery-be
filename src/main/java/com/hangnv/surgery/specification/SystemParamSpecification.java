package com.hangnv.surgery.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.SystemParameterDto;
import com.hangnv.surgery.entity.SystemParameter;

@Component
public class SystemParamSpecification extends BaseSpecification {
	public Specification<SystemParameter> filter(final SystemParameterDto criteria) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNoneBlank(criteria.getKeyword())) {
				String keyword = queryFilter(criteria.getKeyword());
				predicates.add(cb.like(root.get("paramName"), keyword));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
}
