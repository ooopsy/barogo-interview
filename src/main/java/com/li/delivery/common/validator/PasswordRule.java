package com.li.delivery.common.validator;
import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PasswordRuleValidator.class)
public @interface PasswordRule {
    String message() default "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.";
    @SuppressWarnings("rawtypes")
	Class[] groups() default {};
	@SuppressWarnings("rawtypes")
    Class[] payload() default {};
}
