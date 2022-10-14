package com.hangnv.surgery.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hangnv.surgery.dto.UserDto;
import com.hangnv.surgery.entity.User;

@Component
public class UserSpecification extends BaseSpecification {

	public Specification<User> filter(final UserDto criteria) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (StringUtils.isNoneBlank(criteria.getUsername())) {
				String username = queryFilter(criteria.getUsername());
				predicates.add(cb.like(cb.upper(root.get("username")), username));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
	
}
