package com.bless.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * 校验时间必须是今天，不做存在性校验
 */
public class TodayValidator implements ConstraintValidator<Today, Date> {
	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		if (value == null) {
			//不校验
			return true;
		}
		return false;
	}
}
