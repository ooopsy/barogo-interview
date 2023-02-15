package com.li.delivery.common.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;


public class PasswordRuleValidator implements ConstraintValidator<PasswordRule, String> {
	 private final Pattern  number= Pattern.compile(".*[0-9].*");
	 private final Pattern  big= Pattern.compile(".*[A-Z].*");
	 private final Pattern  small= Pattern.compile(".*[a-z].*");
	 private final Pattern  special= Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	 private final int minLength = 12; 
	 
	 private final List<Pattern> patterns;
	 
	 
	 public PasswordRuleValidator() {
		 patterns = new ArrayList<>();
		 patterns.add(number);
		 patterns.add(big);
		 patterns.add(small);
		 patterns.add(special);
	 }
	 
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) {
			return false;
		}
		if (value.length() <= minLength) {
			return false;
		}
		
		long matchCount = patterns.stream().filter(pattern-> pattern.matcher(value).find()).count();
		return matchCount >= 3L;
	}


}