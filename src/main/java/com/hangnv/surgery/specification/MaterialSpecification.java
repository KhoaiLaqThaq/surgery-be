package com.hangnv.surgery.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.entity.Material;

@Component
public class MaterialSpecification extends BaseSpecification {

	public Specification<Material> filter(final MaterialDto criteria) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNoneBlank(criteria.getCode())) {
				String code = queryFilter(criteria.getCode());
				predicates.add(cb.like(root.get("code"), code));
			}
			
			if (StringUtils.isNoneBlank(criteria.getName())) {
				String name = queryFilter(criteria.getName());
				predicates.add(cb.like(cb.upper(root.get("name")), name));
			}
			
			if (StringUtils.isNoneBlank(criteria.getComposition())) {
				String composition = queryFilter(criteria.getComposition());
				predicates.add(cb.like(cb.upper(root.get("composition")), composition));
			}
			
			if (StringUtils.isNoneBlank(criteria.getSuggest())) {
				String suggest = queryFilter(criteria.getSuggest());
				predicates.add(cb.like(cb.upper(root.get("suggest")), suggest));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
	
}
