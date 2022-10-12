package com.hangnv.surgery.specification;

import com.hangnv.surgery.constant.SymbolEnum;

public class BaseSpecification {
	
	public String queryFilter(String fieldName) {
		return SymbolEnum.PERCENT.val
				.concat(fieldName)
				.concat(SymbolEnum.PERCENT.val);
	}
	
}
