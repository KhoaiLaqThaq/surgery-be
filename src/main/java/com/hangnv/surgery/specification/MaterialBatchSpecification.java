package com.hangnv.surgery.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.MaterialBatchDto;
import com.hangnv.surgery.entity.MaterialBatch;

@Component
public class MaterialBatchSpecification extends BaseSpecification {
	
	public Specification<MaterialBatch> filter(final MaterialBatchDto criteria) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNoneBlank(criteria.getCode())) {
				String code = queryFilter(criteria.getCode());
				predicates.add(cb.like(cb.upper(root.get("code")), code));
			}
			
			if (StringUtils.isNoneBlank(criteria.getMaterial().getName())) {
				String materialName = queryFilter(criteria.getMaterial().getName());
				predicates.add(cb.like(cb.upper(root.get("material").get("name")), materialName));
			}
			
			if (criteria.getMinPrice() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
			}
			
			if (criteria.getMaxPrice() != null && criteria.getMaxPrice().compareTo(BigDecimal.ZERO) > 0) {
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}

}
