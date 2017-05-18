package org.serik.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class NumericValidator implements ConstraintValidator<IsNumeric, String> {

    @Override
    public void initialize(IsNumeric constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
	return StringUtils.isNumeric(value);
    }

}